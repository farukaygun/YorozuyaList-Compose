package com.farukaygun.yorozuyalist.data.remote

import com.farukaygun.yorozuyalist.data.remote.dto.AccessTokenDto
import com.farukaygun.yorozuyalist.data.remote.dto.AnimeSeasonalDto
import com.farukaygun.yorozuyalist.data.remote.dto.AnimeSuggestedDto
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
		limit: Int = 10
	): AnimeSeasonalDto

	suspend fun getSuggestedAnime(
		limit: Int = 10,
		offset: Int = 0,
	) : AnimeSuggestedDto
}