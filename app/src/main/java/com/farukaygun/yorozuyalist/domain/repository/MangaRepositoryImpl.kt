package com.farukaygun.yorozuyalist.domain.repository

import com.farukaygun.yorozuyalist.data.remote.APIService
import com.farukaygun.yorozuyalist.data.remote.dto.manga.MangaUserListDto
import com.farukaygun.yorozuyalist.data.repository.MangaRepository

class MangaRepositoryImpl(
	private val api: APIService
) : MangaRepository {
	override suspend fun getUserMangaList(
		status: String,
		sort: String,
		limit: Int,
		offset: Int
	): MangaUserListDto {
		return api.getUserMangaList(
			status = status,
			sort = sort,
			limit = limit,
			offset = offset
		)
	}

	override suspend fun getUserMangaList(
		url: String
	): MangaUserListDto {
		return api.getUserMangaList(
			url = url
		)
	}
}