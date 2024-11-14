package com.farukaygun.yorozuyalist.domain.repository

import androidx.annotation.IntRange
import com.farukaygun.yorozuyalist.data.remote.APIService
import com.farukaygun.yorozuyalist.data.remote.dto.MediaRankingDto
import com.farukaygun.yorozuyalist.data.remote.dto.MyListStatusDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeDetailDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeSearchedDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeSeasonalDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeSuggestedDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeUserListDto
import com.farukaygun.yorozuyalist.data.repository.AnimeRepository

class AnimeRepositoryImpl(
	private val api: APIService
) : AnimeRepository {
	override suspend fun getSeasonalAnime(
		year: Int,
		season: String,
		limit: Int
	): AnimeSeasonalDto {
		return api.getSeasonalAnime(
			year = year,
			season = season,
			limit = limit
		)
	}

	override suspend fun getSeasonalAnime(
		url: String
	): AnimeSeasonalDto {
		return api.getSeasonalAnime(
			url = url
		)
	}

	override suspend fun getSuggestedAnime(
		limit: Int,
		offset: Int
	): AnimeSuggestedDto {
		return api.getSuggestedAnime(
			limit = limit,
			offset = offset
		)
	}

	override suspend fun getSuggestedAnime(
		url: String
	): AnimeSuggestedDto {
		return api.getSuggestedAnime(
			url = url
		)
	}

	override suspend fun getSearchedAnime(
		query: String,
		limit: Int,
		offset: Int
	): AnimeSearchedDto {
		return api.getSearchedAnime(
			query = query,
			limit = limit,
			offset = offset
		)
	}

	override suspend fun getSearchedAnime(
		url: String
	): AnimeSearchedDto {
		return api.getSearchedAnime(
			url = url
		)
	}

	override suspend fun getUserAnimeList(
		status: String?,
		sort: String,
		limit: Int,
		offset: Int
	): AnimeUserListDto {
		return api.getUserAnimeList(
			status = status,
			sort = sort,
			limit = limit,
			offset = offset
		)
	}

	override suspend fun getUserAnimeList(
		url: String
	): AnimeUserListDto {
		return api.getUserAnimeList(
			url = url
		)
	}

	override suspend fun getAnimeDetail(
		id: String
	): AnimeDetailDto {
		return api.getAnimeDetail(
			id = id
		)
	}

	override suspend fun updateMyAnimeListItem(
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
	): MyListStatusDto {
		return api.updateMyAnimeListItem(
			id = id,
			status = status,
			episodeCount = episodeCount,
			score = score,
			startDate = startDate,
			finishDate = finishDate,
			tags = tags,
			priority = priority,
			isRewatching = isRewatching,
			rewatchCount = rewatchCount,
			rewatchValue = rewatchValue,
			comments = comments
		)
	}

	override suspend fun deleteMyAnimeListItem(
		id: Int
	): Boolean {
		return api.deleteMyAnimeListItem(
			id = id
		)
	}

	override suspend fun getAnimeRanking(
		rankingType: String,
		limit: Int,
		offset: Int
	): MediaRankingDto {
		return api.getAnimeRanking(
			rankingType = rankingType,
			limit = limit,
			offset = offset
		)
	}

	override suspend fun getAnimeRanking(
		url: String
	): MediaRankingDto {
		return api.getAnimeRanking(
			url = url
		)
	}
}