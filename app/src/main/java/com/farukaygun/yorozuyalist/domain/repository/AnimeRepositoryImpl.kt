package com.farukaygun.yorozuyalist.domain.repository

import com.farukaygun.yorozuyalist.data.remote.APIService
import com.farukaygun.yorozuyalist.data.remote.dto.UserListDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeSearchedDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeSeasonalDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeSuggestedDto
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

	override suspend fun getSuggestedAnime(
		limit: Int,
		offset: Int
	): AnimeSuggestedDto {
		return api.getSuggestedAnime(
			limit = limit,
			offset = offset
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
		status: String,
		sort: String,
		limit: Int,
		offset: Int
	): UserListDto {
		return api.getUserAnimeList(
			status = status,
			sort = sort,
			limit = limit,
			offset = offset
		)
	}

	override suspend fun getUserAnimeList(
		url: String
	): UserListDto {
		return api.getUserAnimeList(
			url = url
		)
	}
}