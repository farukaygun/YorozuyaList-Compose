package com.farukaygun.yorozuyalist.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.enums.MediaStatus
import com.farukaygun.yorozuyalist.domain.use_case.AnimeUseCase
import com.farukaygun.yorozuyalist.presentation.base.BaseViewModel
import com.farukaygun.yorozuyalist.util.Calendar.Companion.season
import com.farukaygun.yorozuyalist.util.Calendar.Companion.weekDayJapan
import com.farukaygun.yorozuyalist.util.Calendar.Companion.year
import com.farukaygun.yorozuyalist.util.StringValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class HomeViewModel(
	private val animeUseCase: AnimeUseCase,
) : BaseViewModel<HomeState>() {
	override val _state = mutableStateOf(HomeState())

	init {
		onEvent(HomeEvent.InitRequestChain)
	}

	private suspend fun initRequestChain() {
		_state.value = _state.value.copy(isLoading = true)

		getTodayAnime()
		getSeasonalAnime()
		getSuggestedAnime()

		jobs.forEach { it.join() }
		_state.value = _state.value.copy(isLoading = false)
	}

	private fun getTodayAnime(
		limit: Int = 500
	) {
		val animeList = mutableListOf<Data>()

		jobs += animeUseCase.executeSeasonalAnime(
			year = year,
			season = season.apiName,
			limit = limit
		)
			.flowOn(Dispatchers.IO)
			.handleResource(
				onSuccess = { animeData ->
					animeData?.data?.forEach { anime ->
						if (anime.node.broadcast?.dayOfTheWeek.equals(
								weekDayJapan.toString(),
								true
							) && anime.node.status == MediaStatus.CURRENTLY_AIRING.apiName
						)
							animeList.add(anime)
					}.apply {
						_state.value = _state.value.copy(
							animeTodayList = animeList,
							error = ""
						)
					}
				},
				onError = { error ->
					_state.value = HomeState(
						error = error
							?: StringValue.StringResource(R.string.error_fetching).toString(),
						isLoading = false
					)
				}
			)
	}

	private fun getSeasonalAnime() {
		jobs += animeUseCase.executeSeasonalAnime(
			year = year,
			season = season.apiName
		)
			.flowOn(Dispatchers.IO)
			.handleResource(
				onSuccess = { animeData ->
					_state.value = _state.value.copy(
						animeSeasonalList = animeData?.data ?: emptyList(),
						error = ""
					)
				},
				onError = { error ->
					_state.value = _state.value.copy(
						error = error
							?: StringValue.StringResource(R.string.error_fetching).toString(),
						isLoading = false
					)
				}
			)
	}

	private fun getSuggestedAnime() {
		jobs += animeUseCase.executeSuggestedAnime()
			.flowOn(Dispatchers.IO)
			.handleResource(
				onSuccess = { animeData ->
					_state.value = _state.value.copy(
						animeSuggestionList = animeData?.data ?: emptyList(),
						error = ""
					)
				},
				onError = { error ->
					_state.value = _state.value.copy(
						error = error
							?: StringValue.StringResource(R.string.error_fetching).toString(),
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