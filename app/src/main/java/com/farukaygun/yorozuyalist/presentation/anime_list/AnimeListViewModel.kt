package com.farukaygun.yorozuyalist.presentation.anime_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.use_case.AnimeUseCase
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.StringValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AnimeListViewModel(
	private val animeUseCase: AnimeUseCase
) : ViewModel() {
	private val _state = mutableStateOf(AnimeListState())
	val state: State<AnimeListState> = _state

	private var job: Job? = null

	private fun getUserAnimeList() {
		job = animeUseCase.executeUserAnimeList()
			.flowOn(Dispatchers.IO)
			.onEach {
				when (it) {
					is Resource.Success -> {
						_state.value = _state.value.copy(
							userAnimeList = it.data,
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
		job = _state.value.userAnimeList?.paging?.next?.let { nextPageUrl ->
			animeUseCase.executeUserAnimeList(url = nextPageUrl)
				.flowOn(Dispatchers.IO)
				.onEach {
					when (it) {
						is Resource.Success -> {
							val currentData = _state.value.userAnimeList?.data?.toMutableList()
							val newData = it.data?.data
							newData?.let { data -> currentData?.addAll(data) }

							_state.value = _state.value.copy(
								userAnimeList = it.data?.paging?.let { paging ->
									currentData?.let { data ->
										_state.value.userAnimeList?.copy(
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

	fun onEvent(event: AnimeListEvent) {
		when (event) {
			is AnimeListEvent.InitRequestChain -> {
				getUserAnimeList()
			}

			is AnimeListEvent.LoadMore -> {
				loadMore()
			}
		}
	}
}