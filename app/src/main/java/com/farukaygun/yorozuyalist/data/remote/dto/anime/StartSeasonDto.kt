package com.farukaygun.yorozuyalist.data.remote.dto.anime

import com.farukaygun.yorozuyalist.domain.models.anime.StartSeason
import com.farukaygun.yorozuyalist.domain.models.enums.Season
import com.google.gson.annotations.SerializedName

data class StartSeasonDto(
	@SerializedName("year")
	val year: Int,
	@SerializedName("season")
	val season: Season,
)

fun StartSeasonDto.toStartSeason(): StartSeason {
	return StartSeason(
		year = year,
		season = season
	)
}
