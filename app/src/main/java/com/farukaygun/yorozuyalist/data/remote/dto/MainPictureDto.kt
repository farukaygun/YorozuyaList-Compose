package com.farukaygun.yorozuyalist.data.remote.dto

import com.farukaygun.yorozuyalist.domain.models.MainPicture
import com.google.gson.annotations.SerializedName

data class MainPictureDto(
	@SerializedName("medium")
	val medium: String?,
	@SerializedName("large")
	val large: String?
)

fun MainPictureDto.toMainPicture(): MainPicture {
	return MainPicture(
		medium = medium,
		large = large
	)
}