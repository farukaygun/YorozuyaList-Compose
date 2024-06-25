package com.farukaygun.yorozuyalist.domain.model

import com.google.gson.annotations.SerializedName

data class AccessToken(
	@SerializedName("access_token")
	val accessToken: String,
	@SerializedName("expires_in")
	val expiresIn: Long,
	@SerializedName("refresh_token")
	val refreshToken: String,
	@SerializedName("token_type")
	val tokenType: String
)
