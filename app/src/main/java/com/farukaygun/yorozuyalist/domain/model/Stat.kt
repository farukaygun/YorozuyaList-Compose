package com.farukaygun.yorozuyalist.domain.model

import com.farukaygun.yorozuyalist.util.Status

data class Stat(
	val type: Status,
	val value: Int
)