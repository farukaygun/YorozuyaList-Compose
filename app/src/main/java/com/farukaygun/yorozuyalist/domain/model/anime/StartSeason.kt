package com.farukaygun.yorozuyalist.domain.model.anime

import com.google.gson.annotations.SerializedName

data class StartSeason(
	@SerializedName("year")
	val year: Int,

	@SerializedName("season")
	val season: String,
)
