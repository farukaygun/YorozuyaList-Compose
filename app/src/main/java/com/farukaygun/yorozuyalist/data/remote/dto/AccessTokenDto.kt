package com.farukaygun.yorozuyalist.data.remote.dto

import com.farukaygun.yorozuyalist.domain.model.AccessToken
import com.google.gson.annotations.SerializedName

data class AccessTokenDto(
	@SerializedName("access_token")
	val accessToken: String,
	@SerializedName("expires_in")
	val expiresIn: Long,
	@SerializedName("refresh_token")
	val refreshToken: String,
	@SerializedName("token_type")
	val tokenType: String
)

fun AccessTokenDto.toAccessToken(): AccessToken {
	return AccessToken(
		accessToken = accessToken,
		expiresIn = expiresIn,
		refreshToken = refreshToken,
		tokenType = tokenType
	)
}