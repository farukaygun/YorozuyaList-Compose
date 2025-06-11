package com.farukaygun.yorozuyalist.data.remote.dto

import com.farukaygun.yorozuyalist.domain.models.Related
import com.google.gson.annotations.SerializedName

data class RelatedDto(
	@SerializedName("node")
	val node: NodeDto,
	@SerializedName("relation_type")
	val relationType: String,
	@SerializedName("relation_type_formatted")
	val relationTypeFormatted: String
)

fun RelatedDto.toRelated(): Related {
	return Related(
		node = node.toNode(),
		relationType = relationType,
		relationTypeFormatted = relationTypeFormatted
	)
}