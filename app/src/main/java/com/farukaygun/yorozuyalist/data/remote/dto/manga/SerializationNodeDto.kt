package com.farukaygun.yorozuyalist.data.remote.dto.manga

import com.farukaygun.yorozuyalist.domain.models.manga.SerializationNode
import com.google.gson.annotations.SerializedName

data class SerializationNodeDto(
	@SerializedName("id")
	val id: Int,
	@SerializedName("name")
	val name: String
)

fun SerializationNodeDto.toSerializationNode(): SerializationNode {
	return SerializationNode(
		id = id,
		name = name
	)
}