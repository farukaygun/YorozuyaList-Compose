package com.farukaygun.yorozuyalist.data.remote.dto

import com.farukaygun.yorozuyalist.domain.models.MediaRanking
import com.google.gson.annotations.SerializedName

//data class MediaRankingDto(
//	@SerializedName("node")
//	val node: NodeDto,
//	@SerializedName("ranking")
//	val ranking: RankingDto? = null,
//	@SerializedName("ranking_type")
//	val rankingType: RankingType? = null,
//)
//
//fun MediaRankingDto.toMediaRanking(): MediaRanking {
//	return MediaRanking(
//		node = node.toNode(),
//		ranking = ranking?.toRanking(),
//		rankingType = rankingType
//	)
//}

data class MediaRankingDto(
	@SerializedName("data")
	val data: List<DataDto>,
	@SerializedName("paging")
	val paging: PagingDto
)

fun MediaRankingDto.toMediaRanking(): MediaRanking {
	return MediaRanking(
		data = data.map { it.toData() },
		paging = paging.toPaging()
	)
}