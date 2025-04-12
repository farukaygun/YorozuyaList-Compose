package com.farukaygun.yorozuyalist.data.remote.dto

import com.farukaygun.yorozuyalist.domain.models.Recommendation
import com.google.gson.annotations.SerializedName

data class RecommendationDto(
	@SerializedName("node")
	val node: NodeDto,
	@SerializedName("num_recommendations")
	val numRecommendations: Int
)

fun RecommendationDto.toRecommendation(): Recommendation {
	return Recommendation(
		node = node.toNode(),
		numRecommendations = numRecommendations
	)
}