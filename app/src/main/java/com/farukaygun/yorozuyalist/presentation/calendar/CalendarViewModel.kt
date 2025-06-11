package com.farukaygun.yorozuyalist.presentation.calendar

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.enums.MediaStatus
import com.farukaygun.yorozuyalist.domain.use_case.AnimeUseCase
import com.farukaygun.yorozuyalist.presentation.base.BaseViewModel
import com.farukaygun.yorozuyalist.util.Calendar.Companion.season
import com.farukaygun.yorozuyalist.util.Calendar.Companion.year
import com.farukaygun.yorozuyalist.util.Calendar.WeekDays
import com.farukaygun.yorozuyalist.util.StringValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CalendarViewModel(
	private val animeUseCase: AnimeUseCase
) : BaseViewModel<CalendarState>() {
	override val _state = mutableStateOf(CalendarState())

	init {
		onEvent(CalendarEvent.GetWeeklyAnimeList)
	}

	private fun getWeeklyAnime(
		limit: Int = 500
	) {
		val animesByDay = mutableMapOf<WeekDays, List<Data>>()

		jobs += animeUseCase.executeSeasonalAnime(
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
						error = ""
					)
				},
				onError = { error ->
					_state.value = CalendarState(
						error = error
							?: StringValue.StringResource(R.string.error_fetching).toString(),
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