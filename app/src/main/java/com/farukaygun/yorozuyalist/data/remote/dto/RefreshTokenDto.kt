package com.farukaygun.yorozuyalist.data.remote.dto

import com.farukaygun.yorozuyalist.domain.model.RefreshToken
import com.google.gson.annotations.SerializedName

data class RefreshTokenDto(
	@SerializedName("access_token")
	val accessToken: String,
	@SerializedName("expires_in")
	val expiresIn: Long,
	@SerializedName("refresh_token")
	val refreshToken: String,
	@SerializedName("token_type")
	val tokenType: String
)

fun RefreshTokenDto.toRefreshToken(): RefreshToken {
	return RefreshToken(
		accessToken = accessToken,
		expiresIn = expiresIn,
		refreshToken = refreshToken,
		tokenType = tokenType
	)
}
