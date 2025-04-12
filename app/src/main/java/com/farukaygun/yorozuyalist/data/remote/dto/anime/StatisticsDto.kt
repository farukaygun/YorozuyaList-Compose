package com.farukaygun.yorozuyalist.data.remote.dto.anime

import com.farukaygun.yorozuyalist.domain.models.anime.Statistics
import com.farukaygun.yorozuyalist.domain.models.enums.MediaStatus
import com.google.gson.annotations.SerializedName

data class StatisticsDto(
	@SerializedName("status")
	val status: MediaStatus,
	@SerializedName("num_list_users")
	val numListUsers: Int
)

fun StatisticsDto.toStatistics(): Statistics {
	return Statistics(
		status = status,
		numListUsers = numListUsers
	)
}