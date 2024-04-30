package com.farukaygun.yorozuyalist.domain.repository

import com.farukaygun.yorozuyalist.data.remote.APIService
import com.farukaygun.yorozuyalist.data.remote.dto.AnimeSeasonalDto
import com.farukaygun.yorozuyalist.data.remote.dto.AnimeSuggestedDto
import com.farukaygun.yorozuyalist.data.repository.AnimeRepository

class AnimeRepositoryImpl(private val api: APIService) : AnimeRepository {
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
}