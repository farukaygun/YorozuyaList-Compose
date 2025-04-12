package com.farukaygun.yorozuyalist.data.remote.dto.anime

import com.farukaygun.yorozuyalist.domain.models.anime.Season
import com.google.gson.annotations.SerializedName

data class SeasonDto(
	@SerializedName("season")
	val season: String,
	@SerializedName("year")
	val year: Int
)

fun SeasonDto.toSeason(): Season {
	return Season(
		season = season,
		year = year
	)
}
