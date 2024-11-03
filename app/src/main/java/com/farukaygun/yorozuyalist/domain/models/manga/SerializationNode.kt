package com.farukaygun.yorozuyalist.domain.models.manga

import com.google.gson.annotations.SerializedName

data class SerializationNode(
	@SerializedName("id")
	val id: Int,
	@SerializedName("name")
	val name: String,
)