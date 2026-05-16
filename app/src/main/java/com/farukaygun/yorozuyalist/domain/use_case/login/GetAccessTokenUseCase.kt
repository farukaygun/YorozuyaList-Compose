package com.farukaygun.yorozuyalist.domain.use_case.login

import com.farukaygun.yorozuyalist.data.remote.dto.toAccessToken
import com.farukaygun.yorozuyalist.domain.models.AccessToken
import com.farukaygun.yorozuyalist.domain.repository.LoginRepository
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.toAppError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAccessTokenUseCase(private val repository: LoginRepository) {
	operator fun invoke(
		code: String,
		clientId: String,
		codeVerifier: String
	): Flow<Resource<AccessToken>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.getAccessToken(code, clientId, codeVerifier).toAccessToken()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}
}
