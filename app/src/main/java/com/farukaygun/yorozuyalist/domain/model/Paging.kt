package com.farukaygun.yorozuyalist.domain.model

import com.google.gson.annotations.SerializedName

data class Paging(
	@SerializedName("next")
	val next: String? = null,

	@SerializedName("previous")
	val previous: String? = null,
)
