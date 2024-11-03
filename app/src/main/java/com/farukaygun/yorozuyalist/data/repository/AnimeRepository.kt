package com.farukaygun.yorozuyalist.data.repository

import androidx.annotation.IntRange
import com.farukaygun.yorozuyalist.data.remote.dto.MyListStatusDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeDetailDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeSearchedDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeSeasonalDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeSuggestedDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeUserListDto

interface AnimeRepository {
	suspend fun getSeasonalAnime(
		year: Int,
		season: String,
		limit: Int
	): AnimeSeasonalDto

	suspend fun getSeasonalAnime(
		url: String
	): AnimeSeasonalDto

	suspend fun getSuggestedAnime(
		limit: Int,
		offset: Int
	): AnimeSuggestedDto

	suspend fun getSuggestedAnime(
		url: String
	): AnimeSuggestedDto

	suspend fun getSearchedAnime(
		query: String,
		limit: Int,
		offset: Int
	): AnimeSearchedDto

	suspend fun getSearchedAnime(
		url: String
	): AnimeSearchedDto

	suspend fun getUserAnimeList(
		status: String?,
		sort: String,
		limit: Int,
		offset: Int
	): AnimeUserListDto

	suspend fun getUserAnimeList(
		url: String
	): AnimeUserListDto

	suspend fun getAnimeDetail(
		id: String
	): AnimeDetailDto

	suspend fun updateMyAnimeListItem(
		id: Int,
		status: String?,
		episodeCount: Int?,
		@IntRange(0,10) score: Int?,
		startDate: String?,
		finishDate: String?,
		tags: String?,
		priority: Int?,
		isRewatching: Boolean?,
		@IntRange(0, 5) rewatchCount: Int?,
		@IntRange(0, 2) rewatchValue: Int?,
		comments: String?
	): MyListStatusDto

	suspend fun deleteMyAnimeListItem(
		id: Int
	): Boolean
}