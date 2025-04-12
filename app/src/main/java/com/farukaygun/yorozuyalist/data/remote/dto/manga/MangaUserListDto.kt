package com.farukaygun.yorozuyalist.data.remote.dto.manga

import com.farukaygun.yorozuyalist.data.remote.dto.DataDto
import com.farukaygun.yorozuyalist.data.remote.dto.toData
import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.Paging
import com.farukaygun.yorozuyalist.domain.models.manga.MangaUserList
import com.google.gson.annotations.SerializedName

data class MangaUserListDto(
	@SerializedName("data")
	val data: List<DataDto>,
	@SerializedName("paging")
	val paging: Paging
)

fun MangaUserListDto.toMangaUserList(): MangaUserList {
	return MangaUserList(
		data = data.map { it.toData() },
		paging = paging
	)
}