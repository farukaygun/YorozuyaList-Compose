package com.farukaygun.yorozuyalist.domain.models

import com.google.gson.annotations.SerializedName

data class Recommendation(
	@SerializedName("node")
	val node: Node,
	@SerializedName("num_recommendations")
	val numRecommendations: Int,
)