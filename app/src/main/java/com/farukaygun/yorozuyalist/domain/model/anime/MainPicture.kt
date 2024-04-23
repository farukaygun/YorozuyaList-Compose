package com.farukaygun.yorozuyalist.domain.model.anime

import com.google.gson.annotations.SerializedName

data class MainPicture(
	@SerializedName("medium")
	val medium: String,

	@SerializedName("large")
	val large: String,
)
