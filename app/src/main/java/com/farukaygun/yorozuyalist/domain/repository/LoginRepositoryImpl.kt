package com.farukaygun.yorozuyalist.domain.repository

import com.farukaygun.yorozuyalist.data.remote.APIService
import com.farukaygun.yorozuyalist.data.remote.dto.AccessTokenDto
import com.farukaygun.yorozuyalist.data.remote.dto.RefreshTokenDto
import com.farukaygun.yorozuyalist.data.repository.LoginRepository

class LoginRepositoryImpl(private val api: APIService) : LoginRepository {
	override suspend fun getAccessToken(
		code: String,
		clientId: String,
		codeVerifier: String,
	): AccessTokenDto {
		return api.getAuthToken(
			code,
			clientId,
			codeVerifier,
		)
	}

	override suspend fun getRefreshToken(
		grantType: String,
		refreshToken: String
	): RefreshTokenDto {
		return api.getRefreshToken(
			grantType,
			refreshToken
		)
	}
}