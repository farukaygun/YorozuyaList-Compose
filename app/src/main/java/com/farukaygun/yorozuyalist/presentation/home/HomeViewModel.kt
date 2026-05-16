package com.farukaygun.yorozuyalist.presentation.home

import androidx.lifecycle.viewModelScope
import com.farukaygun.yorozuyalist.domain.models.enums.MediaStatus
import com.farukaygun.yorozuyalist.domain.use_case.anime.GetSeasonalAnimeUseCase
import com.farukaygun.yorozuyalist.domain.use_case.anime.GetSuggestedAnimeUseCase
import com.farukaygun.yorozuyalist.presentation.base.BaseViewModel
import com.farukaygun.yorozuyalist.util.Calendar.Companion.season
import com.farukaygun.yorozuyalist.util.Calendar.Companion.weekDayJapan
import com.farukaygun.yorozuyalist.util.Calendar.Companion.year
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class HomeViewModel(
	private val getSeasonalAnime: GetSeasonalAnimeUseCase,
	private val getSuggestedAnime: GetSuggestedAnimeUseCase
) : BaseViewModel<HomeState>() {
	override val _state = MutableStateFlow(HomeState())

	init {
		onEvent(HomeEvent.InitRequestChain)
	}

	private suspend fun initRequestChain() {
		_state.value = _state.value.copy(isLoading = true)

		loadTodayAnime()
		loadSeasonalAnime()
		loadSuggestedAnime()

		jobs.joinAll()
		_state.value = _state.value.copy(isLoading = false)
	}

	private fun loadTodayAnime(
		limit: Int = 500
	) {
		jobs += getSeasonalAnime(
			year = year,
			season = season.apiName,
			limit = limit
		)
			.flowOn(Dispatchers.IO)
			.handleResource(
				onSuccess = { animeData ->
					val filteredList = animeData?.data?.filter { anime ->
						anime.node.broadcast?.dayOfTheWeek.equals(weekDayJapan.toString(), true) &&
								anime.node.status == MediaStatus.CURRENTLY_AIRING.apiName
					} ?: emptyList()

					_state.value = _state.value.copy(
						animeTodayList = filteredList,
						error = null
					)
				},
				onError = { error ->
					_state.value = HomeState(
						error = error,
						isLoading = false
					)
				}
			)
	}

	private fun loadSeasonalAnime() {
		jobs += getSeasonalAnime(
			year = year,
			season = season.apiName
		)
			.flowOn(Dispatchers.IO)
			.handleResource(
				onSuccess = { animeData ->
					_state.value = _state.value.copy(
						animeSeasonalList = animeData?.data ?: emptyList(),
						error = null
					)
				},
				onError = { error ->
					_state.value = _state.value.copy(
						error = error,
						isLoading = false
					)
				}
			)
	}

	private fun loadSuggestedAnime() {
		jobs += getSuggestedAnime()
			.flowOn(Dispatchers.IO)
			.handleResource(
				onSuccess = { animeData ->
					_state.value = _state.value.copy(
						animeSuggestionList = animeData?.data ?: emptyList(),
						error = null
					)
				},
				onError = { error ->
					_state.value = _state.value.copy(
						error = error,
						isLoading = false
					)
				}
			)
	}

	fun onEvent(event: HomeEvent) {
		when (event) {
			is HomeEvent.InitRequestChain -> viewModelScope.launch { initRequestChain() }
		}
	}
}
