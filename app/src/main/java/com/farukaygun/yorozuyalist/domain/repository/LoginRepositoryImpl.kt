package com.farukaygun.yorozuyalist.domain.repository

import com.farukaygun.yorozuyalist.data.remote.APIService
import com.farukaygun.yorozuyalist.data.remote.dto.AuthTokenDto
import com.farukaygun.yorozuyalist.data.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val api: APIService) : LoginRepository {
	override suspend fun getAuthToken(
		code: String,
		clientId: String,
		codeVerifier: String,
	): AuthTokenDto {
		return api.getAuthToken(
			code,
			clientId,
			codeVerifier,
		)
	}
}