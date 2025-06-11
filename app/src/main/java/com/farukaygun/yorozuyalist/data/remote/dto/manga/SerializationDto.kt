package com.farukaygun.yorozuyalist.data.remote.dto.manga

import com.farukaygun.yorozuyalist.domain.models.manga.Serialization
import com.google.gson.annotations.SerializedName

data class SerializationDto(
	@SerializedName("node")
	val node: SerializationNodeDto
)

fun SerializationDto.toSerialization(): Serialization {
	return Serialization(
		node = node.toSerializationNode()
	)
}