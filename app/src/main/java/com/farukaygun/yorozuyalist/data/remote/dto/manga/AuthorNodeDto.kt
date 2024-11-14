package com.farukaygun.yorozuyalist.data.remote.dto.manga

import com.farukaygun.yorozuyalist.domain.models.manga.AuthorNode
import com.google.gson.annotations.SerializedName

data class AuthorNodeDto(
	@SerializedName("id")
	val id: Int,
	@SerializedName("first_name")
	val firstName: String,
	@SerializedName("last_name")
	val lastName: String,
)

fun AuthorNodeDto.toAuthorNode(): AuthorNode {
	return AuthorNode(
		id = id,
		firstName = firstName,
		lastName = lastName
	)
}