package com.farukaygun.yorozuyalist.domain.use_case.login

import com.farukaygun.yorozuyalist.data.remote.dto.toRefreshToken
import com.farukaygun.yorozuyalist.domain.models.RefreshToken
import com.farukaygun.yorozuyalist.domain.repository.LoginRepository
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.toAppError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRefreshTokenUseCase(private val repository: LoginRepository) {
	operator fun invoke(
		grantType: String,
		refreshToken: String
	): Flow<Resource<RefreshToken>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.getRefreshToken(grantType, refreshToken).toRefreshToken()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}
}
