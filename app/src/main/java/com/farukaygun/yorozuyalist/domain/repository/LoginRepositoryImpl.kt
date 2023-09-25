package com.farukaygun.yorozuyalist.domain.repository

import com.farukaygun.yorozuyalist.data.remote.APIService
import com.farukaygun.yorozuyalist.data.remote.dto.AuthTokenDto
import com.farukaygun.yorozuyalist.data.repository.LoginRepository
import com.farukaygun.yorozuyalist.util.Constants
import com.farukaygun.yorozuyalist.util.Private
import com.farukaygun.yorozuyalist.util.Util
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val api: APIService) : LoginRepository {
	override suspend fun getAuthToken(): AuthTokenDto {
		return api.getAuthToken()
	}

}