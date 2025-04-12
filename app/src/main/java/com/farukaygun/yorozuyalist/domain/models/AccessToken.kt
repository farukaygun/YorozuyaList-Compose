package com.farukaygun.yorozuyalist.domain.models

data class AccessToken(
	val accessToken: String,
	val expiresIn: Long,
	val refreshToken: String,
	val tokenType: String
)
