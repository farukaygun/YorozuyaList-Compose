package com.farukaygun.yorozuyalist.domain.models.manga

import com.google.gson.annotations.SerializedName

data class Serialization(
	@SerializedName("node")
	val node: SerializationNode,
)