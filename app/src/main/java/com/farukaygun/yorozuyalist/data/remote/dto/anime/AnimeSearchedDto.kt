package com.farukaygun.yorozuyalist.data.remote.dto.anime

import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.Paging
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeSearched
import com.google.gson.annotations.SerializedName

data class AnimeSearchedDto(
	@SerializedName("data")
	val data: List<Data>,
	@SerializedName("paging")
	val paging: Paging
)

fun AnimeSearchedDto.toAnimeSearched(): AnimeSearched {
	return AnimeSearched(
		data = data,
		paging = paging
	)
}