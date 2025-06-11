package com.farukaygun.yorozuyalist.presentation.calendar

import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.util.Calendar

data class CalendarState(
	val animeWeeklyList: MutableMap<Calendar.WeekDays, List<Data>> = mutableMapOf(),
	val error: String = "",
	val isLoading: Boolean = false
)