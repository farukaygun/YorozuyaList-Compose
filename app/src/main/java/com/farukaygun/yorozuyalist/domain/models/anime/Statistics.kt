package com.farukaygun.yorozuyalist.domain.models.anime

import com.farukaygun.yorozuyalist.domain.models.enums.MediaStatus
import com.google.gson.annotations.SerializedName

data class Statistics(
	@SerializedName("status")
	val status: MediaStatus,
	@SerializedName("num_list_users")
	val numListUsers: Int,
)