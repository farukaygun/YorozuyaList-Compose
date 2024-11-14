package com.farukaygun.yorozuyalist.data.remote.dto.manga

import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.Paging
import com.farukaygun.yorozuyalist.domain.models.manga.MangaUserList
import com.google.gson.annotations.SerializedName

data class MangaUserListDto(
	@SerializedName("data")
	val data: List<Data>,
	@SerializedName("paging")
	val paging: Paging
)

fun MangaUserListDto.toMangaUserList(): MangaUserList {
	return MangaUserList(
		data = data,
		paging = paging
	)
}