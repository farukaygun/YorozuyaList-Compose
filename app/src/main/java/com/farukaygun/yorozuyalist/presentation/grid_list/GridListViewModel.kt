package com.farukaygun.yorozuyalist.presentation.grid_list

import com.farukaygun.yorozuyalist.domain.interfaces.MediaList
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeUserList
import com.farukaygun.yorozuyalist.domain.use_case.anime.GetAnimeRankingUseCase
import com.farukaygun.yorozuyalist.domain.use_case.anime.GetSeasonalAnimeUseCase
import com.farukaygun.yorozuyalist.domain.use_case.anime.GetSuggestedAnimeUseCase
import com.farukaygun.yorozuyalist.domain.use_case.manga.GetMangaRankingUseCase
import com.farukaygun.yorozuyalist.presentation.base.BaseViewModel
import com.farukaygun.yorozuyalist.util.Calendar.Companion.season
import com.farukaygun.yorozuyalist.util.Calendar.Companion.year
import com.farukaygun.yorozuyalist.util.enums.GridListType
import com.farukaygun.yorozuyalist.util.enums.RankingType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn

class GridListViewModel(
	private val getSeasonalAnime: GetSeasonalAnimeUseCase,
	private val getSuggestedAnime: GetSuggestedAnimeUseCase,
	private val getAnimeRanking: GetAnimeRankingUseCase,
	private val getMangaRanking: GetMangaRankingUseCase
) : BaseViewModel<GridListState>() {
	override val _state = MutableStateFlow(GridListState())

	private fun getList() {
		fun handleSuccess(list: MediaList?) {
			_state.value = _state.value.copy(gridList = list, isLoading = false, error = null)
		}

		jobs += when (_state.value.type) {
			GridListType.SUGGESTED_ANIME_LIST -> getSuggestedAnime()
				.flowOn(Dispatchers.IO)
				.handleResource(
					onSuccess = ::handleSuccess,
					onError = { error -> _state.value = _state.value.copy(error = error, isLoading = false) },
					onLoading = { _state.value = _state.value.copy(isLoading = true) }
				)
			GridListType.SEASONAL_ANIME_LIST -> getSeasonalAnime(year = year, season = season.apiName)
				.flowOn(Dispatchers.IO)
				.handleResource(
					onSuccess = ::handleSuccess,
					onError = { error -> _state.value = _state.value.copy(error = error, isLoading = false) },
					onLoading = { _state.value = _state.value.copy(isLoading = true) }
				)
			GridListType.RANKING_ANIME_LIST -> getAnimeRanking(rankingType = RankingType.ALL.value)
				.flowOn(Dispatchers.IO)
				.handleResource(
					onSuccess = ::handleSuccess,
					onError = { error -> _state.value = _state.value.copy(error = error, isLoading = false) },
					onLoading = { _state.value = _state.value.copy(isLoading = true) }
				)
			GridListType.RANKING_MANGA_LIST -> getMangaRanking(rankingType = RankingType.ALL.value)
				.flowOn(Dispatchers.IO)
				.handleResource(
					onSuccess = ::handleSuccess,
					onError = { error -> _state.value = _state.value.copy(error = error, isLoading = false) },
					onLoading = { _state.value = _state.value.copy(isLoading = true) }
				)
		}
	}

	private fun loadMore() {
		_state.value.gridList?.paging?.next?.let { nextPageUrl ->
			fun mergeAndUpdate(list: MediaList?) {
				val currentData = _state.value.gridList?.data?.toMutableList() ?: mutableListOf()
				val newData = list?.data ?: emptyList()
				val mergedData = (currentData + newData).distinctBy { media -> media.node.id }
				_state.value = _state.value.copy(
					gridList = list?.paging?.let { paging ->
						AnimeUserList(data = mergedData, paging = paging)
					},
					isLoadingMore = false,
					error = null
				)
			}

			jobs += when (_state.value.type) {
				GridListType.SUGGESTED_ANIME_LIST -> getSuggestedAnime(url = nextPageUrl)
					.flowOn(Dispatchers.IO)
					.handleResource(
						onSuccess = ::mergeAndUpdate,
						onError = { error -> _state.value = _state.value.copy(error = error, isLoadingMore = false) },
						onLoading = { _state.value = _state.value.copy(isLoadingMore = true) }
					)
				GridListType.SEASONAL_ANIME_LIST -> getSeasonalAnime(url = nextPageUrl)
					.flowOn(Dispatchers.IO)
					.handleResource(
						onSuccess = ::mergeAndUpdate,
						onError = { error -> _state.value = _state.value.copy(error = error, isLoadingMore = false) },
						onLoading = { _state.value = _state.value.copy(isLoadingMore = true) }
					)
				GridListType.RANKING_ANIME_LIST -> getAnimeRanking(url = nextPageUrl)
					.flowOn(Dispatchers.IO)
					.handleResource(
						onSuccess = ::mergeAndUpdate,
						onError = { error -> _state.value = _state.value.copy(error = error, isLoadingMore = false) },
						onLoading = { _state.value = _state.value.copy(isLoadingMore = true) }
					)
				GridListType.RANKING_MANGA_LIST -> getMangaRanking(url = nextPageUrl)
					.flowOn(Dispatchers.IO)
					.handleResource(
						onSuccess = ::mergeAndUpdate,
						onError = { error -> _state.value = _state.value.copy(error = error, isLoadingMore = false) },
						onLoading = { _state.value = _state.value.copy(isLoadingMore = true) }
					)
			}
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
