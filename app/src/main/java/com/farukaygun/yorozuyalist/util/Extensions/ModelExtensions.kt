package com.farukaygun.yorozuyalist.util.Extensions

import androidx.compose.runtime.Composable
import co.yml.charts.common.extensions.isNotNull
import com.farukaygun.yorozuyalist.domain.interfaces.MediaDetail
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeDetail
import com.farukaygun.yorozuyalist.domain.models.anime.Broadcast
import com.farukaygun.yorozuyalist.domain.models.anime.StartSeason
import com.farukaygun.yorozuyalist.domain.models.manga.MangaDetail
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object ModelExtensions {
	@Composable
	fun MediaDetail.formatMediaDuration() = when (this) {
		is AnimeDetail -> {
			if (numEpisodes > 0) {
				"$numEpisodes Episodes"
			} else "Unknown"
		}
		is MangaDetail -> {
			if (numVolumes > 0) {
				"$numVolumes Volumes"
			} else "Unknown"
		}
		else -> "N/A"
	}

	@Composable
	fun StartSeason.formatStartSeason() = if (season.isNotNull() && year != 0) {
		"${season.format()} $year"
	} else {
		"N/A"
	}

	@Composable
	fun AnimeDetail.episodeDurationLocalized() = when {
		averageEpisodeDuration <= 0 -> "N/A"
		averageEpisodeDuration > 3600 -> {
			val duration = averageEpisodeDuration.toDuration(DurationUnit.SECONDS)
			duration.toComponents { hours, minutes, _, _ ->
				"$hours hour $minutes minutes"
			}
		}
		averageEpisodeDuration == 3600 -> "1 hour"
		averageEpisodeDuration >= 60 -> "${averageEpisodeDuration / 60} minutes"
		averageEpisodeDuration < 60 -> "<1 minute"
		else -> "N/A"
	}

	fun Broadcast.formatBroadcast() = if (dayOfTheWeek.isNotEmpty() && startTime?.isNotEmpty() == true) {
		"${dayOfTheWeek.replaceFirstChar { it.uppercase() }} $startTime"
	} else {
		"N/A"
	}
}