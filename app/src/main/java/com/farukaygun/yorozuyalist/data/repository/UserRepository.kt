package com.farukaygun.yorozuyalist.data.repository

import com.farukaygun.yorozuyalist.data.remote.dto.user.UserDto

interface UserRepository {
	suspend fun getUserProfile() : UserDto
}