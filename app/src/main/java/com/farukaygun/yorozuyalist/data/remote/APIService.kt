package com.farukaygun.yorozuyalist.data.remote

import com.farukaygun.yorozuyalist.data.remote.dto.AccessTokenDto
import com.farukaygun.yorozuyalist.data.remote.dto.RefreshTokenDto
import com.farukaygun.yorozuyalist.data.remote.dto.UserListDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeSearchedDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeSeasonalDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeSuggestedDto
import com.farukaygun.yorozuyalist.data.remote.dto.user.UserDto

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
	) : UserListDto

	suspend fun getUserAnimeList(
		url: String
	) : UserListDto

	suspend fun getUserMangaList(
		status: String,
		sort: String,
		limit: Int,
		offset: Int
	) : UserListDto

	suspend fun getUserMangaList(
		url: String
	) : UserListDto

	suspend fun getUserProfile() : UserDto

}