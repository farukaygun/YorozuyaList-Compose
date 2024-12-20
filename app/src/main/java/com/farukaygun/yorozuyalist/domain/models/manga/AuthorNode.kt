package com.farukaygun.yorozuyalist.domain.models.manga

import com.google.gson.annotations.SerializedName

data class AuthorNode(
	@SerializedName("id")
	val id: Int,
	@SerializedName("first_name")
	val firstName: String,
	@SerializedName("last_name")
	val lastName: String,
)