package com.farukaygun.yorozuyalist.domain.models.anime

import com.google.gson.annotations.SerializedName

data class Broadcast(
	@SerializedName("day_of_the_week")
	val dayOfTheWeek: String,
	@SerializedName("start_time")
	val startTime: String,
)
