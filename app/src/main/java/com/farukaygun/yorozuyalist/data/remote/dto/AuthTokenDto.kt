package com.farukaygun.yorozuyalist.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.farukaygun.yorozuyalist.domain.model.AuthToken

data class AuthTokenDto(
	@SerializedName("access_token")
	val accessToken: String,
	@SerializedName("expires_in")
	val expiresIn: Int,
	@SerializedName("refresh_token")
	val refreshToken: String,
	@SerializedName("token_type")
	val tokenType: String
)

fun AuthTokenDto.toAuthToken(): AuthToken {
	return AuthToken(
		accessToken = accessToken,
		expiresIn = expiresIn,
		refreshToken = refreshToken,
		tokenType = tokenType
	)
}