package com.farukaygun.yorozuyalist.domain.use_case

import com.farukaygun.yorozuyalist.data.remote.dto.user.toUser
import com.farukaygun.yorozuyalist.data.repository.UserRepository
import com.farukaygun.yorozuyalist.domain.model.user.User
import com.farukaygun.yorozuyalist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserUseCase(
	private val repository: UserRepository
) {
	fun executeUser(

	) : Flow<Resource<User>> = flow {
		try {
			emit(Resource.Loading())

			val userProfile = repository.getUserProfile()

			emit(Resource.Success(userProfile.toUser()))
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
}