package com.farukaygun.yorozuyalist.data.remote

import com.farukaygun.yorozuyalist.data.remote.dto.AccessTokenDto
import com.farukaygun.yorozuyalist.data.remote.dto.AnimeSearchedDto
import com.farukaygun.yorozuyalist.data.remote.dto.AnimeSeasonalDto
import com.farukaygun.yorozuyalist.data.remote.dto.AnimeSuggestedDto
import com.farukaygun.yorozuyalist.data.remote.dto.AnimeUserListDto
import com.farukaygun.yorozuyalist.data.remote.dto.MangaUserListDto
import com.farukaygun.yorozuyalist.data.remote.dto.RefreshTokenDto

interface APIService {
	suspend fun getAuthToken(
		code: String,
		clientId: String,
		codeVerifier: String
	): AccessTokenDto

	suspend fun getRefreshToken(
		grantType: String,
		refreshToken: String
	) : RefreshTokenDto

	suspend fun getSeasonalAnime(
		year: Int,
		season: String,
		limit: Int
	): AnimeSeasonalDto

	suspend fun getSuggestedAnime(
		limit: Int,
		offset: Int,
	) : AnimeSuggestedDto

	suspend fun getSearchedAnime(
		query: String,
		limit: Int,
		offset: Int
	) : AnimeSearchedDto

	suspend fun getSearchedAnime(
		url: String
	) : AnimeSearchedDto

	suspend fun getUserAnimeList(
		status: String,
		sort: String,
		limit: Int,
		offset: Int
	) : AnimeUserListDto

	suspend fun getUserAnimeList(
		url: String
	) : AnimeUserListDto

	suspend fun getUserMangaList(
		status: String,
		sort: String,
		limit: Int,
		offset: Int
	) : MangaUserListDto

	suspend fun getUserMangaList(
		url: String
	) : MangaUserListDto

}