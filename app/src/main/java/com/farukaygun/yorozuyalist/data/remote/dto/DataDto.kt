package com.farukaygun.yorozuyalist.data.remote.dto

import com.farukaygun.yorozuyalist.domain.models.Data
import com.google.gson.annotations.SerializedName

data class DataDto(
	@SerializedName("node")
	val node: NodeDto,
	@SerializedName("ranking")
	val ranking: RankingDto?,
	@SerializedName("ranking_type")
	var rankingType: String?,
	@SerializedName("list_status")
	val myListStatus: MyListStatusDto?
)

fun DataDto.toData(): Data {
	return Data(
		node = node.toNode(),
		ranking = ranking?.toRanking(),
		rankingType = rankingType,
		myListStatus = myListStatus?.toMyListStatus()
	)
}