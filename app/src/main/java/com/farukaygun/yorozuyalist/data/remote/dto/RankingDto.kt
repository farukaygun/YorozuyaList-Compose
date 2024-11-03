package com.farukaygun.yorozuyalist.data.remote.dto

import com.farukaygun.yorozuyalist.domain.models.Ranking
import com.google.gson.annotations.SerializedName

data class RankingDto(
	@SerializedName("rank")
	val rank: Int,
)

fun RankingDto.toRanking(): Ranking {
	return Ranking(
		rank = rank
	)
}