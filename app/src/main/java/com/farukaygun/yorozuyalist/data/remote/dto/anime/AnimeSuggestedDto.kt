package com.farukaygun.yorozuyalist.data.remote.dto.anime

import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.Paging
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeSuggested
import com.google.gson.annotations.SerializedName

data class AnimeSuggestedDto(
	@SerializedName("data")
	val data: List<Data>,

	@SerializedName("paging")
	val paging: Paging,
)

fun AnimeSuggestedDto.toAnimeSuggested(): AnimeSuggested {
	return AnimeSuggested(
		data = data,
		paging = paging
	)
}