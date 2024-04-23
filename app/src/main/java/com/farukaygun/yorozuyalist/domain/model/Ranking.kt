package com.farukaygun.yorozuyalist.domain.model

import com.google.gson.annotations.SerializedName

data class Ranking(
	@SerializedName("rank")
	val rank: Int,
)
