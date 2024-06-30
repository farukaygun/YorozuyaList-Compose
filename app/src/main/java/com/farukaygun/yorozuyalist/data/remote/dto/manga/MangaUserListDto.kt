package com.farukaygun.yorozuyalist.data.remote.dto.manga

import com.farukaygun.yorozuyalist.domain.model.Data
import com.farukaygun.yorozuyalist.domain.model.MangaUserList
import com.farukaygun.yorozuyalist.domain.model.Paging
import com.google.gson.annotations.SerializedName

data class MangaUserListDto(
	@SerializedName("data")
	val data: List<Data>,
	@SerializedName("paging")
	val paging: Paging
)

fun MangaUserListDto.toMangaUserList() : MangaUserList {
	return MangaUserList(
		data = data,
		paging = paging
	)
}