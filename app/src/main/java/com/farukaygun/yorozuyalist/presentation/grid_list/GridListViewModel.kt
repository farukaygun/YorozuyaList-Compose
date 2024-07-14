package com.farukaygun.yorozuyalist.presentation.grid_list

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.model.anime.AnimeUserList
import com.farukaygun.yorozuyalist.domain.use_case.AnimeUseCase
import com.farukaygun.yorozuyalist.domain.use_case.MangaUseCase
import com.farukaygun.yorozuyalist.util.Calendar.Companion.getYearAndSeason
import com.farukaygun.yorozuyalist.util.GridListType
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.StringValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GridListViewModel(
	private val animeUseCase: AnimeUseCase,
	private val mangaUseCase: MangaUseCase
) : ViewModel() {
	private val _state = mutableStateOf(GridListState())
	val state = _state

	private var job: Job? = null

	private fun getList() {
		val (year, season) = getYearAndSeason()
		val getList = when (_state.value.type) {
			GridListType.SUGGESTED_ANIME_LIST -> animeUseCase.executeSuggestedAnime()
			GridListType.SEASONAL_ANIME_LIST -> animeUseCase.executeSeasonalAnime(
				year,
				season.value
			)
		}

		println("getList: $getList")

		job = getList
			.flowOn(Dispatchers.IO)
			.onEach {
				when (it) {
					is Resource.Success -> {
						_state.value = _state.value.copy(
							gridList = it.data,
							isLoading = false,
							error = ""
						)
					}

					is Resource.Error -> {
						_state.value = _state.value.copy(
							error = it.message
								?: StringValue.StringResource(R.string.error_fetching).toString(),
						)
					}

					is Resource.Loading -> {
						_state.value = _state.value.copy(isLoading = true)
					}
				}
			}.launchIn(viewModelScope)
	}

	private fun loadMore() {
		job = _state.value.gridList?.paging?.next?.let { nextPageUrl ->
			val loadMore = when (_state.value.type) {
				GridListType.SUGGESTED_ANIME_LIST -> animeUseCase.executeSuggestedAnime(url = nextPageUrl)
				GridListType.SEASONAL_ANIME_LIST -> animeUseCase.executeSeasonalAnime(url = nextPageUrl)
			}

			loadMore
				.flowOn(Dispatchers.IO)
				.onEach {
					when (it) {
						is Resource.Success -> {
							val currentData = _state.value.gridList?.data?.toMutableStateList()
							val newData = it.data?.data
							newData?.let { data -> currentData?.addAll(data) }

							_state.value = _state.value.copy(
								gridList = it.data?.paging?.let { paging ->
									currentData?.let { data ->
										AnimeUserList(
											data = data,
											paging = paging
										)
									}
								},
								isLoadingMore = false,
								error = ""
							)
						}

						is Resource.Error -> {
							_state.value = _state.value.copy(
								error = it.message
									?: StringValue.StringResource(R.string.error_fetching)
										.toString(),
								isLoadingMore = false
							)
						}

						is Resource.Loading -> {
							_state.value = _state.value.copy(isLoadingMore = true)
						}
					}
				}.launchIn(viewModelScope)
		}
	}

	fun onEvent(event: GridListEvent) {
		when (event) {
			GridListEvent.InitRequestChain -> getList()
			GridListEvent.LoadMore -> loadMore()
		}
	}
}