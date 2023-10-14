package com.farukaygun.yorozuyalist.domain.model

import com.google.gson.annotations.SerializedName

data class AuthToken(
	@SerializedName("access_token")
	val accessToken: String,
	@SerializedName("expires_in")
	val expiresIn: Int,
	@SerializedName("refresh_token")
	val refreshToken: String,
	@SerializedName("token_type")
	val tokenType: String
)
