package com.farukaygun.yorozuyalist.domain.use_case

import com.farukaygun.yorozuyalist.data.remote.dto.toAccessToken
import com.farukaygun.yorozuyalist.data.remote.dto.toRefreshToken
import com.farukaygun.yorozuyalist.data.repository.LoginRepository
import com.farukaygun.yorozuyalist.domain.models.AccessToken
import com.farukaygun.yorozuyalist.domain.models.RefreshToken
import com.farukaygun.yorozuyalist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginUseCase(private val repository: LoginRepository) {
	fun executeAuthToken(
		code: String,
		clientId: String,
		codeVerifier: String
	): Flow<Resource<AccessToken>> = flow {
		try {
			emit(Resource.Loading())

			val authToken = repository.getAccessToken(
				code,
				clientId,
				codeVerifier
			)

			emit(Resource.Success(authToken.toAccessToken()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}

	fun executeRefreshToken(
		grantType: String,
		refreshToken: String
	): Flow<Resource<RefreshToken>> = flow {
		try {
			emit(Resource.Loading())

			val newRefreshToken = repository.getRefreshToken(
				grantType,
				refreshToken
			)

			emit(Resource.Success(newRefreshToken.toRefreshToken()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}
}