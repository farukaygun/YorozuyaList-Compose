package com.farukaygun.yorozuyalist.data.remote

import com.farukaygun.yorozuyalist.data.remote.dto.AuthTokenDto
import com.farukaygun.yorozuyalist.data.remote.dto.RefreshTokenDto

interface APIService {
	suspend fun getAuthToken(
		code: String,
		clientId: String,
		codeVerifier: String
	): AuthTokenDto

	suspend fun getRefreshToken(
		grantType: String,
		refreshToken: String
	) : RefreshTokenDto
}