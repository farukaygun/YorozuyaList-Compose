package com.farukaygun.yorozuyalist.data.repository

import com.farukaygun.yorozuyalist.data.remote.APIService
import com.farukaygun.yorozuyalist.data.remote.dto.user.UserDto
import com.farukaygun.yorozuyalist.domain.repository.UserRepository

class UserRepositoryImpl(
	private val api: APIService
) : UserRepository {
	override suspend fun getUserProfile(): UserDto {
		return api.getUserProfile()
	}
}
