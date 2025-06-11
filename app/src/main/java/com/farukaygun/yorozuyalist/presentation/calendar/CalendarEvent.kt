package com.farukaygun.yorozuyalist.presentation.calendar

sealed class CalendarEvent {
	data object GetWeeklyAnimeList : CalendarEvent()
}