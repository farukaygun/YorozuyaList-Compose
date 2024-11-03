package com.farukaygun.yorozuyalist.domain.models

import com.google.gson.annotations.SerializedName

data class Ranking(
	@SerializedName("rank")
	val rank: Int,
)
