package com.farukaygun.yorozuyalist.domain.models

import com.farukaygun.yorozuyalist.domain.models.enums.MyListMediaStatus

data class Stat(
	val type: MyListMediaStatus,
	val value: Int
)