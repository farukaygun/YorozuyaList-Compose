package com.farukaygun.yorozuyalist.domain.use_case.user

import com.farukaygun.yorozuyalist.data.remote.dto.user.toUser
import com.farukaygun.yorozuyalist.domain.models.user.User
import com.farukaygun.yorozuyalist.domain.repository.UserRepository
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.toAppError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserProfileUseCase(private val repository: UserRepository) {
	operator fun invoke(): Flow<Resource<User>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.getUserProfile().toUser()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}
}
