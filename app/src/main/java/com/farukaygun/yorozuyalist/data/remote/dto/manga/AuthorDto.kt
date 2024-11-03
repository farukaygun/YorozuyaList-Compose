package com.farukaygun.yorozuyalist.data.remote.dto.manga

import com.farukaygun.yorozuyalist.domain.models.manga.Author
import com.google.gson.annotations.SerializedName

data class AuthorDto(
	@SerializedName("node")
	val node: AuthorNodeDto,

	@SerializedName("role")
	val role: String,
)

fun AuthorDto.toAuthor(): Author {
	return Author(
		node = node.toAuthorNode(),
		role = role
	)
}