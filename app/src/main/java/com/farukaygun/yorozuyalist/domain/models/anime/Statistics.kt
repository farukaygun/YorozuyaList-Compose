package com.farukaygun.yorozuyalist.domain.models.anime

import com.farukaygun.yorozuyalist.domain.models.enums.MediaStatus

data class Statistics(
	val status: MediaStatus,
	val numListUsers: Int
)