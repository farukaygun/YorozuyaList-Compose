package com.farukaygun.yorozuyalist.util

import com.farukaygun.yorozuyalist.R
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class Calendar {
	companion object {
		private val tokyoTimeZone = TimeZone.of("Asia/Tokyo")
		private val currentDateTime =
			Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
		private val currentDateTimeJapan = Clock.System.now().toLocalDateTime(tokyoTimeZone)

		val weekDay: DayOfWeek
			get() = currentDateTime.dayOfWeek
		val weekDayJapan: DayOfWeek
			get() = currentDateTimeJapan.dayOfWeek
		val currentJapanHour: LocalTime
			get() = currentDateTimeJapan.time
		val year: Int
			get() = currentDateTime.year
		val season: Seasons
			get() = when (currentDateTime.month) {
				Month.JANUARY, Month.FEBRUARY, Month.MARCH -> Seasons.WINTER
				Month.APRIL, Month.MAY, Month.JUNE -> Seasons.SPRING
				Month.JULY, Month.AUGUST, Month.SEPTEMBER -> Seasons.SUMMER
				Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER -> Seasons.FALL
			}
	}

	enum class Seasons(val displayName: String, val apiName: String, val icon: Int) {
		WINTER("Winter", "winter", R.drawable.winter_24),
		SPRING("Spring", "spring", R.drawable.spring_24px),
		SUMMER("Summer", "summer", R.drawable.summer_24px),
		FALL("Fall", "fall",  R.drawable.fall_24px);
	}
}