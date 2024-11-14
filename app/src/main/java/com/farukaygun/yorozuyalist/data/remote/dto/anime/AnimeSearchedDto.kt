package com.farukaygun.yorozuyalist.data.remote.dto.anime

import com.farukaygun.yorozuyalist.data.remote.dto.DataDto
import com.farukaygun.yorozuyalist.data.remote.dto.PagingDto
import com.farukaygun.yorozuyalist.data.remote.dto.toData
import com.farukaygun.yorozuyalist.data.remote.dto.toPaging
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeSearched
import com.google.gson.annotations.SerializedName

data class AnimeSearchedDto(
	@SerializedName("data")
	val data: List<DataDto>,
	@SerializedName("paging")
	val paging: PagingDto
)

fun AnimeSearchedDto.toAnimeSearched(): AnimeSearched {
	return AnimeSearched(
		data = data.map { it.toData() },
		paging = paging.toPaging()
	)
}