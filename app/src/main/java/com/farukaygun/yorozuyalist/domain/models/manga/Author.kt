package com.farukaygun.yorozuyalist.domain.models.manga

import com.google.gson.annotations.SerializedName

data class Author(
	@SerializedName("node")
	val node: AuthorNode,

	@SerializedName("role")
	val role: String,
)
