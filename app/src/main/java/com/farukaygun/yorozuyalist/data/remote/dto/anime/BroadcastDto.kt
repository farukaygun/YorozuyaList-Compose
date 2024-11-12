package com.farukaygun.yorozuyalist.data.remote.dto.anime

import com.farukaygun.yorozuyalist.domain.models.anime.Broadcast
import com.google.gson.annotations.SerializedName

data class BroadcastDto(
	@SerializedName("day_of_the_week")
	val dayOfTheWeek: String,
	@SerializedName("start_time")
	val startTime: String?,
)

fun BroadcastDto.toBroadcast(): Broadcast {
	return Broadcast(
		dayOfTheWeek = dayOfTheWeek,
		startTime = startTime
	)
}
