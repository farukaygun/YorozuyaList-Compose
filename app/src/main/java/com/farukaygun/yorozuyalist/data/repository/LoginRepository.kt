package com.farukaygun.yorozuyalist.data.repository

import com.farukaygun.yorozuyalist.data.remote.dto.AuthTokenDto
import com.farukaygun.yorozuyalist.data.remote.dto.RefreshTokenDto

interface LoginRepository {
	suspend fun getAccessToken(
		code: String,
		clientId: String,
		codeVerifier: String
	): AuthTokenDto

	suspend fun getRefreshToken(
		grantType: String,
		refreshToken: String
	): RefreshTokenDto
}