package com.farukaygun.yorozuyalist.data.remote.dto.anime

import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.Paging
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeUserList
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