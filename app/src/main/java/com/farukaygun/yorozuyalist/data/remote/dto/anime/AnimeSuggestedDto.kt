package com.farukaygun.yorozuyalist.data.remote.dto.anime

import com.farukaygun.yorozuyalist.data.remote.dto.DataDto
import com.farukaygun.yorozuyalist.data.remote.dto.PagingDto
import com.farukaygun.yorozuyalist.data.remote.dto.toData
import com.farukaygun.yorozuyalist.data.remote.dto.toPaging
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeSuggested
import com.google.gson.annotations.SerializedName

data class AnimeSuggestedDto(
	@SerializedName("data")
	val data: List<DataDto>,
	@SerializedName("paging")
	val paging: PagingDto
)

fun AnimeSuggestedDto.toAnimeSuggested(): AnimeSuggested {
	return AnimeSuggested(
		data = data.map { it.toData() },
		paging = paging.toPaging()
	)
}