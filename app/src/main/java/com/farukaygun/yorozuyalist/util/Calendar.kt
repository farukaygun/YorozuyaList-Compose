package com.farukaygun.yorozuyalist.util

import com.farukaygun.yorozuyalist.R
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.math.abs

class Calendar {
	companion object {
		private val tokyoTimeZone = TimeZone.of("Asia/Tokyo")
		private val currentDateTime =
			kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
		private val currentDateTimeJapan =
            kotlin.time.Clock.System.now().toLocalDateTime(tokyoTimeZone)

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
		
		fun convertTimeToLocalTimezone(
			timeString: String,
			sourceTimezoneId: String = "JST"
		): String {
			return try {
				val (hour, minute) = timeString.split(":").map { it.toInt() }
				val today = kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.UTC).date
				val sourceDateTime = LocalDateTime(
					year = today.year,
					month = today.month,
					dayOfMonth = today.dayOfMonth,
					hour = hour,
					minute = minute,
					second = 0,
					nanosecond = 0
				)
				val sourceInstant = sourceDateTime.toInstant(TimeZone.of(sourceTimezoneId))
				val localDateTime = sourceInstant.toLocalDateTime(TimeZone.currentSystemDefault())
				
				"${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}"
			} catch (e: Exception) {
				timeString
			}
		}

		fun getFormattedTimeDifference(timeString: String): String {
			val localTime = convertTimeToLocalTimezone(timeString)
			val (hour, minute) = localTime.split(":").map { it.toInt() }

			val currentDateTime =
                kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
			val givenDateTime = LocalDateTime(
				year = currentDateTime.year,
				month = currentDateTime.month,
				dayOfMonth = currentDateTime.dayOfMonth,
				hour = hour,
				minute = minute,
				second = 0,
				nanosecond = 0
			)

			val differenceInMinutes = givenDateTime.toInstant(TimeZone.currentSystemDefault())
				.minus(kotlin.time.Clock.System.now())
				.inWholeMinutes

			return when {
				differenceInMinutes == 0L -> "Şu an"
				abs(differenceInMinutes) < 60 -> {
					if (differenceInMinutes < 0) "${abs(differenceInMinutes)} dakika önce"
					else "${abs(differenceInMinutes)} dakika sonra"
				}
				else -> {
					val hours = abs(differenceInMinutes) / 60
					val remainingMinutes = abs(differenceInMinutes) % 60

					if (remainingMinutes == 0L) {
						if (differenceInMinutes < 0) "$hours saat önce" else "$hours saat sonra"
					} else {
						if (differenceInMinutes < 0) "$hours saat $remainingMinutes dakika önce" else "$hours saat $remainingMinutes dakika sonra"
					}
				}
			}
		}
	}

	enum class Seasons(val displayName: String, val apiName: String, val icon: Int) {
		WINTER("Winter", "winter", R.drawable.winter_24),
		SPRING("Spring", "spring", R.drawable.spring_24px),
		SUMMER("Summer", "summer", R.drawable.summer_24px),
		FALL("Fall", "fall", R.drawable.fall_24px);
	}

	enum class WeekDays(val displayName: String, val apiName: String) {
		MONDAY("Monday", "monday"),
		TUESDAY("Tuesday", "tuesday"),
		WEDNESDAY("Wednesday", "wednesday"),
		THURSDAY("Thursday", "thursday"),
		FRIDAY("Friday", "friday"),
		SATURDAY("Saturday", "saturday"),
		SUNDAY("Sunday", "sunday");
	}
}