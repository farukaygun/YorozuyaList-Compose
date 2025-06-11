package com.farukaygun.yorozuyalist.presentation.grid_list

import androidx.compose.runtime.mutableStateOf
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.interfaces.MediaList
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeUserList
import com.farukaygun.yorozuyalist.domain.use_case.AnimeUseCase
import com.farukaygun.yorozuyalist.domain.use_case.MangaUseCase
import com.farukaygun.yorozuyalist.presentation.base.BaseViewModel
import com.farukaygun.yorozuyalist.util.Calendar.Companion.season
import com.farukaygun.yorozuyalist.util.Calendar.Companion.year
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.StringValue
import com.farukaygun.yorozuyalist.util.enums.GridListType
import com.farukaygun.yorozuyalist.util.enums.RankingType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GridListViewModel(
	private val animeUseCase: AnimeUseCase,
	private val mangaUseCase: MangaUseCase
) : BaseViewModel<GridListState>() {
	override val _state = mutableStateOf(GridListState())

	@Suppress("UNCHECKED_CAST")
	private fun getList() {
		val getList = when (_state.value.type) {
			GridListType.SUGGESTED_ANIME_LIST -> animeUseCase.executeSuggestedAnime()
			GridListType.SEASONAL_ANIME_LIST -> animeUseCase.executeSeasonalAnime(
				year = year,
				season = season.apiName
			)

			GridListType.RANKING_ANIME_LIST -> animeUseCase.executeGetAnimeRanking(rankingType = RankingType.ALL.value)
			GridListType.RANKING_MANGA_LIST -> mangaUseCase.executeGetMangaRanking(rankingType = RankingType.ALL.value)
		} as Flow<Resource<MediaList>>

		jobs += getList
			.flowOn(Dispatchers.IO)
			.handleResource(
				onSuccess = { animeList ->
					_state.value = _state.value.copy(
						gridList = animeList,
						isLoading = false,
						error = ""
					)
				},
				onError = { error ->
					_state.value = _state.value.copy(
						error = error
							?: StringValue.StringResource(R.string.error_fetching).toString(),
						isLoading = false
					)
				},
				onLoading = {
					_state.value = _state.value.copy(isLoading = true)
				}
			)
	}

	@Suppress("UNCHECKED_CAST")
	private fun loadMore() {
		_state.value.gridList?.paging?.next?.let { nextPageUrl ->
			val loadMore = when (_state.value.type) {
				GridListType.SUGGESTED_ANIME_LIST -> animeUseCase.executeSuggestedAnime(url = nextPageUrl)
				GridListType.SEASONAL_ANIME_LIST -> animeUseCase.executeSeasonalAnime(url = nextPageUrl)
				GridListType.RANKING_ANIME_LIST -> animeUseCase.executeGetAnimeRanking(url = nextPageUrl)
				GridListType.RANKING_MANGA_LIST -> mangaUseCase.executeGetMangaRanking(url = nextPageUrl)
			} as Flow<Resource<MediaList>>

			jobs += loadMore
				.flowOn(Dispatchers.IO)
				.handleResource(
					onSuccess = { animeList ->
						val currentData =
							_state.value.gridList?.data?.toMutableList() ?: mutableListOf()
						val newData = animeList?.data ?: emptyList()
						val mergedData =
							(currentData + newData).distinctBy { media -> media.node.id }

						_state.value = _state.value.copy(
							gridList = animeList?.paging?.let { paging ->
								AnimeUserList(
									data = mergedData,
									paging = paging
								)
							},
							isLoadingMore = false,
							error = ""
						)
					},
					onError = { error ->
						_state.value = _state.value.copy(
							error = error
								?: StringValue.StringResource(R.string.error_fetching).toString(),
							isLoadingMore = false
						)
					},
					onLoading = {
						_state.value = _state.value.copy(isLoadingMore = true)
					}
				)
		}
	}

	fun updateType(type: GridListType) {
		_state.value = _state.value.copy(
			type = type
		)
	}

	fun onEvent(event: GridListEvent) {
		when (event) {
			GridListEvent.InitRequestChain -> getList()
			GridListEvent.LoadMore -> loadMore()
		}
	}
}