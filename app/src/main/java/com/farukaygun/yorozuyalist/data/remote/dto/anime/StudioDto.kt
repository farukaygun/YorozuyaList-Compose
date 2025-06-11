package com.farukaygun.yorozuyalist.data.remote.dto.anime

import com.farukaygun.yorozuyalist.domain.models.anime.Studio
import com.google.gson.annotations.SerializedName

data class StudioDto(
	@SerializedName("id")
	val id: Int,
	@SerializedName("name")
	val name: String
)

fun StudioDto.toStudio(): Studio {
	return Studio(
		id = id,
		name = name
	)
}