package com.farukaygun.yorozuyalist.data.remote.dto.anime

import com.farukaygun.yorozuyalist.domain.model.Data
import com.farukaygun.yorozuyalist.domain.model.Paging
import com.farukaygun.yorozuyalist.domain.model.anime.AnimeUserList
import com.google.gson.annotations.SerializedName

class AnimeUserListDto(
	@SerializedName("data")
	val data: List<Data>,
	@SerializedName("paging")
	val paging: Paging
)

fun AnimeUserListDto.toAnimeUserList(): AnimeUserList {
	return AnimeUserList(
		data = data,
		paging = paging
	)
}