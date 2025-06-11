package com.farukaygun.yorozuyalist.domain.models

data class Data(
	val node: Node,
	val ranking: Ranking?,
	var rankingType: String?,
	val myListStatus: MyListStatus?
)
