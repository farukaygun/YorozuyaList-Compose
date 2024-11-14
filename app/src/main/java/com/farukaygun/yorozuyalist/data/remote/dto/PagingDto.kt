package com.farukaygun.yorozuyalist.data.remote.dto

import com.farukaygun.yorozuyalist.domain.models.Paging
import com.google.gson.annotations.SerializedName

data class PagingDto(
	@SerializedName("next")
	val next: String? = null,
	@SerializedName("previous")
	val previous: String? = null,
)

fun PagingDto.toPaging(): Paging {
	return Paging(
		next = next,
		previous = previous
	)
}
