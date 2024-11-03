package com.farukaygun.yorozuyalist.domain.models

import com.google.gson.annotations.SerializedName

data class MainPicture(
	@SerializedName("medium")
	val medium: String,
	@SerializedName("large")
	val large: String?,
)
