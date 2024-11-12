package com.farukaygun.yorozuyalist.data.repository

import com.farukaygun.yorozuyalist.data.remote.dto.MediaRankingDto
import com.farukaygun.yorozuyalist.data.remote.dto.MyListStatusDto
import com.farukaygun.yorozuyalist.data.remote.dto.manga.MangaDetailDto
import com.farukaygun.yorozuyalist.data.remote.dto.manga.MangaUserListDto

interface MangaRepository {
	suspend fun getUserMangaList(
		status: String?,
		sort: String,
		limit: Int,
		offset: Int
	): MangaUserListDto

	suspend fun getUserMangaList(
		url: String
	): MangaUserListDto

	suspend fun getMangaDetail(
		id: String
	) : MangaDetailDto

	suspend fun updateMyMangaListItem(
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
	) : MyListStatusDto

	suspend fun deleteMyMangaListItem(
		id: Int
	): Boolean

	suspend fun getMangaRanking(
		rankingType: String,
		limit: Int,
		offset: Int
	): MediaRankingDto

	suspend fun getMangaRanking(
		url: String
	): MediaRankingDto
}