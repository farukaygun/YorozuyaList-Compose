package com.farukaygun.yorozuyalist.domain.model.anime

import com.google.gson.annotations.SerializedName

data class Season(
	@SerializedName("season")
	val season: String,

	@SerializedName("year")
	val year: Int,
)
