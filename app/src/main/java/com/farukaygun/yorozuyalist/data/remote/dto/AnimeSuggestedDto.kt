package com.farukaygun.yorozuyalist.data.remote.dto

import com.farukaygun.yorozuyalist.domain.model.Data
import com.farukaygun.yorozuyalist.domain.model.Paging
import com.farukaygun.yorozuyalist.domain.model.anime.AnimeSuggested
import com.google.gson.annotations.SerializedName

data class AnimeSuggestedDto(
	@SerializedName("data")
	val `data`: List<Data>,

	@SerializedName("paging")
	val paging: Paging,
)

fun AnimeSuggestedDto.toAnimeSuggested() : AnimeSuggested {
	return AnimeSuggested(
		data = data,
		paging = paging
	)
}