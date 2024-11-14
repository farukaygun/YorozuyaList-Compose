package com.farukaygun.yorozuyalist.domain.models.anime

import com.farukaygun.yorozuyalist.domain.models.enums.Season
import com.google.gson.annotations.SerializedName

data class StartSeason(
	@SerializedName("year")
	val year: Int,
	@SerializedName("season")
	val season: Season,
)
