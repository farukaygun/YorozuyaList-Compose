package com.farukaygun.yorozuyalist.presentation.calendar

import androidx.lifecycle.viewModelScope
import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.enums.MediaStatus
import com.farukaygun.yorozuyalist.domain.use_case.anime.GetSeasonalAnimeUseCase
import com.farukaygun.yorozuyalist.presentation.base.BaseViewModel
import com.farukaygun.yorozuyalist.util.Calendar.Companion.season
import com.farukaygun.yorozuyalist.util.Calendar.Companion.year
import com.farukaygun.yorozuyalist.util.Calendar.WeekDays
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CalendarViewModel(
	private val getSeasonalAnime: GetSeasonalAnimeUseCase
) : BaseViewModel<CalendarState>() {
	override val _state = MutableStateFlow(CalendarState())

	init {
		onEvent(CalendarEvent.GetWeeklyAnimeList)
	}

	private fun getWeeklyAnime(
		limit: Int = 500
	) {
		val animesByDay = mutableMapOf<WeekDays, List<Data>>()

		jobs += getSeasonalAnime(
			year = year,
			season = season.apiName,
			limit = limit
		)
			.flowOn(Dispatchers.IO)
			.handleResource(
				onSuccess = { animeData ->
					WeekDays.entries.forEach { day ->
						val animesForDay = animeData?.data?.filter { anime ->
							anime.node.broadcast?.dayOfTheWeek?.equals(
								day.displayName,
								true
							) == true &&
									anime.node.status == MediaStatus.CURRENTLY_AIRING.apiName
						} ?: emptyList()

						animesByDay[day] = animesForDay
					}

					_state.value = _state.value.copy(
						animeWeeklyList = animesByDay,
						error = null
					)
				},
				onError = { error ->
					_state.value = CalendarState(
						error = error,
						isLoading = false
					)
				}
			)
	}

	fun onEvent(event: CalendarEvent) {
		when (event) {
			is CalendarEvent.GetWeeklyAnimeList -> viewModelScope.launch { getWeeklyAnime() }
		}
	}
}
