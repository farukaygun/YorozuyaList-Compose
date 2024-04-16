package com.farukaygun.yorozuyalist.domain.use_case

import com.farukaygun.yorozuyalist.data.remote.dto.toAuthToken
import com.farukaygun.yorozuyalist.data.remote.dto.toRefreshToken
import com.farukaygun.yorozuyalist.data.repository.LoginRepository
import com.farukaygun.yorozuyalist.domain.model.AuthToken
import com.farukaygun.yorozuyalist.domain.model.RefreshToken
import com.farukaygun.yorozuyalist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: LoginRepository) {
	fun executeAuthToken(
		code: String,
		clientId: String,
		codeVerifier: String
	): Flow<Resource<AuthToken>> = flow {
		try {
			emit(Resource.Loading())

			val authToken = repository.getAccessToken(
				code,
				clientId,
				codeVerifier
			)

			emit(Resource.Success(authToken.toAuthToken()))
		} catch (e: Exception) {
			e.printStackTrace()
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
		}
	}
}