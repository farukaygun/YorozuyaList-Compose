package com.farukaygun.yorozuyalist.data.repository

import com.farukaygun.yorozuyalist.data.remote.dto.UserListDto

interface MangaRepository {
	suspend fun getUserMangaList(
		status: String,
		sort: String,
		limit: Int,
		offset: Int
	): UserListDto

	suspend fun getUserMangaList(
		url: String
	): UserListDto
}