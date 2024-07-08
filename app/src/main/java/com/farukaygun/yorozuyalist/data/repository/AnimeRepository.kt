package com.farukaygun.yorozuyalist.data.repository

import com.farukaygun.yorozuyalist.data.remote.dto.UserListDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeSearchedDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeSeasonalDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeSuggestedDto

interface AnimeRepository {
	suspend fun getSeasonalAnime(
		year: Int,
		season: String,
		limit: Int
	): AnimeSeasonalDto

	suspend fun getSuggestedAnime(
		limit: Int,
		offset: Int
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
		status: String,
		sort: String,
		limit: Int,
		offset: Int
	): UserListDto

	suspend fun getUserAnimeList(
		url: String
	): UserListDto
}