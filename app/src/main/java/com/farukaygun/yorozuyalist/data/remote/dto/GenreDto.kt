package com.farukaygun.yorozuyalist.data.remote.dto

import com.farukaygun.yorozuyalist.domain.models.Genre
import com.google.gson.annotations.SerializedName

data class GenreDto(
	@SerializedName("id")
	val id: Int,

	@SerializedName("name")
	val name: String,
)

fun GenreDto.toGenre(): Genre {
	return Genre(
		id = id,
		name = name
	)
}