package com.farukaygun.yorozuyalist.domain.models

import com.google.gson.annotations.SerializedName

data class Data(
	@SerializedName("node")
	val node: Node,
	@SerializedName("ranking")
	val ranking: Ranking?,
	@SerializedName("ranking_type")
	var rankingType: String?,
	@SerializedName("list_status")
	val myListStatus: MyListStatus?,
)
