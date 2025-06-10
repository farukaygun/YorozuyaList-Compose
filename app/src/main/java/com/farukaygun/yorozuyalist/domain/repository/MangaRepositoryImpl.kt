package com.farukaygun.yorozuyalist.domain.repository

import com.farukaygun.yorozuyalist.data.remote.APIService
import com.farukaygun.yorozuyalist.data.remote.dto.MediaRankingDto
import com.farukaygun.yorozuyalist.data.remote.dto.MyListStatusDto
import com.farukaygun.yorozuyalist.data.remote.dto.manga.MangaDetailDto
import com.farukaygun.yorozuyalist.data.remote.dto.manga.MangaUserListDto
import com.farukaygun.yorozuyalist.data.repository.MangaRepository

class MangaRepositoryImpl(
	private val api: APIService
) : MangaRepository {
	override suspend fun getUserMangaList(
		status: String?,
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

	override suspend fun getMangaDetail(
		id: String
	): MangaDetailDto {
		return api.getMangaDetail(
			id = id
		)
	}

	override suspend fun updateMyMangaListItem(
		id: Int,
		status: String?,
		chapterCount: Int?,
		volumeCount: Int?,
		score: Int?,
		startDate: String?,
		finishDate: String?,
		tags: String?,
		priority: Int?,
		isRereading: Boolean?,
		rereadCount: Int?,
		rereadValue: Int?,
		comments: String?
	): MyListStatusDto {
		return api.updateMyMangaListItem(
			id = id,
			status = status,
			chapterCount = chapterCount,
			volumeCount = volumeCount,
			score = score,
			startDate = startDate,
			finishDate = finishDate,
			tags = tags,
			priority = priority,
			isRereading = isRereading,
			rereadCount = rereadCount,
			rereadValue = rereadValue,
			comments = comments
		)
	}

	override suspend fun deleteMyMangaListItem(
		id: Int
	): Boolean {
		return api.deleteMyMangaListItem(
			id = id
		)
	}

	override suspend fun getMangaRanking(
		rankingType: String,
		limit: Int,
		offset: Int
	): MediaRankingDto {
		return api.getMangaRanking(
			rankingType = rankingType,
			limit = limit,
			offset = offset
		)
	}

	override suspend fun getMangaRanking(
		url: String
	): MediaRankingDto {
		return api.getMangaRanking(
			url = url
		)
	}
}