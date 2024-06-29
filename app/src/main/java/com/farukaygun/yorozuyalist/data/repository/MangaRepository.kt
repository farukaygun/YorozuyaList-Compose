package com.farukaygun.yorozuyalist.data.repository

import com.farukaygun.yorozuyalist.data.remote.dto.MangaUserListDto

interface MangaRepository {
	suspend fun getUserMangaList(
		status: String,
		sort: String,
		limit: Int,
		offset: Int
	): MangaUserListDto

	suspend fun getUserMangaList(
		url: String
	): MangaUserListDto
}