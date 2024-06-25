package com.farukaygun.yorozuyalist.util

import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class Calendar {
	companion object {
		private val tokyoTimeZone = TimeZone.of("Asia/Tokyo")

		val weekDay: DayOfWeek
			get() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).dayOfWeek
		val weekDayJapan: DayOfWeek
			get() = Clock.System.now().toLocalDateTime(tokyoTimeZone).dayOfWeek
		val currentJapanHour: LocalTime
			get() = Clock.System.now().toLocalDateTime(tokyoTimeZone).time

		fun getYearAndSeason(): Pair<Int, Seasons> {
			val currentLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
			val year = currentLocalDateTime.year
			val month = currentLocalDateTime.month

			return when (month) {
				Month.JANUARY, Month.FEBRUARY, Month.MARCH -> Pair(year, Seasons.WINTER)
				Month.APRIL, Month.MAY, Month.JUNE -> Pair(year, Seasons.SPRING)
				Month.JULY, Month.AUGUST, Month.SEPTEMBER -> Pair(year, Seasons.SUMMER)
				Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER -> Pair(year, Seasons.FALL)
			}
		}
	}

	enum class Seasons(val value: String) {
		WINTER("winter"),
		SPRING("spring"),
		SUMMER("summer"),
		FALL("fall")
	}
}