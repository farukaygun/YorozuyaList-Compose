package com.farukaygun.yorozuyalist.data.remote

import com.farukaygun.yorozuyalist.data.remote.dto.AuthTokenDto

interface APIService {
	suspend fun getAuthToken(
		code: String,
		clientId: String,
		codeVerifier: String
	): AuthTokenDto
}