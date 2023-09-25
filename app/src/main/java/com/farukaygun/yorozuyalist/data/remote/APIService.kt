package com.farukaygun.yorozuyalist.data.remote

import com.farukaygun.yorozuyalist.data.remote.dto.AuthTokenDto

interface APIService {
	suspend fun getAuthToken(): AuthTokenDto
}