package com.farukaygun.yorozuyalist.data.repository

import com.farukaygun.yorozuyalist.data.remote.dto.AuthTokenDto

interface LoginRepository {
	suspend fun getAuthToken(): AuthTokenDto
}