package com.farukaygun.yorozuyalist.domain.use_case

import com.farukaygun.yorozuyalist.data.remote.dto.toAuthToken
import com.farukaygun.yorozuyalist.data.repository.LoginRepository
import com.farukaygun.yorozuyalist.domain.model.AuthToken
import com.farukaygun.yorozuyalist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: LoginRepository) {
	fun executeAuthToken(): Flow<Resource<AuthToken>> = flow {
		try {
			emit(Resource.Loading())

			val authToken = repository.getAuthToken()

			emit(Resource.Success(authToken.toAuthToken()))
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
}