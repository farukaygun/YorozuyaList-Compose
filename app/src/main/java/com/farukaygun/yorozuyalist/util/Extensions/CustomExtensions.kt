package com.farukaygun.yorozuyalist.util.Extensions

import android.content.Context
import android.content.Intent
import android.icu.text.DecimalFormat
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

object CustomExtensions {
	/** Open link in Chrome Custom Tabs */
	fun Context.openCustomTab(url: String) {
		CustomTabsIntent.Builder()
			.build().apply {
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
				launchUrl(this@openCustomTab, url.toUri())
			}
	}

	fun Number.localizeNumber(): String {
		return DecimalFormat.getNumberInstance(java.util.Locale.getDefault()).format(this)
	}

	fun String.formatDate(): String {
		val ymdFormat = LocalDate.Format {
			day(); char(' '); monthName(MonthNames.ENGLISH_ABBREVIATED); char(' '); year()
		}

		val ymFormat = DateTimeComponents.Format {
			year(); char('-'); monthNumber()
		}

		return if (this.length == 10)
			LocalDate.parse(this).format(ymdFormat)
		else {
			val date = ymFormat.parse(this)
			return "${
				date.month?.name?.lowercase()?.replaceFirstChar { it.uppercase() }
			} ${date.year}"
		}
	}

	fun String.formatToAbbreviatedDate(): String {
		val instantTime = Instant.parse(this)
		val localDateTime = instantTime.toLocalDateTime(TimeZone.currentSystemDefault())
		val customFormat = LocalDateTime.Format {
			day(); char(' '); monthName(MonthNames.ENGLISH_ABBREVIATED); char(' '); year()
		}

		return localDateTime.format(customFormat)
	}

	fun String.formatToISODate(): String {
		if (this.isEmpty()) return ""

		val customFormat = LocalDate.Format {
			day(); char(' '); monthName(MonthNames.ENGLISH_ABBREVIATED); char(' '); year()
		}

		val customlocalDateTime = LocalDate.parse(this, customFormat)
		val isoFormat = LocalDate.Format {
			year(); char('-'); monthNumber(); char('-'); day()
		}

		return customlocalDateTime.format(isoFormat)
	}

	fun Long.formatToAbbreviatedDate(): String {
		val instantTime = Instant.fromEpochMilliseconds(this)
		val localDateTime = instantTime.toLocalDateTime(TimeZone.currentSystemDefault())
		val customFormat = LocalDateTime.Format {
			day(); char(' '); monthName(MonthNames.ENGLISH_ABBREVIATED); char(' '); year()
		}

		return localDateTime.format(customFormat)
	}

	fun Int.formatScore() = when (this) {
		0 -> "-"
		1 -> "Appalling"
		2 -> "Horrible"
		3 -> "Very Bad"
		4 -> "Bad"
		5 -> "Average"
		6 -> "Fine"
		7 -> "Good"
		8 -> "Very Good"
		9 -> "Great"
		10 -> "Masterpiece"
		else -> "N/A"
	}

	fun Int.formatPriority() = when (this) {
		0 -> "Low"
		1 -> "Medium"
		2 -> "High"
		else -> "N/A"
	}

	fun Int.formatRewatchValue() = when (this) {
		0 -> "-"
		1 -> "Very Low"
		2 -> "Low"
		3 -> "Medium"
		4 -> "High"
		5 -> "Very High"
		else -> "N/A"
	}
}