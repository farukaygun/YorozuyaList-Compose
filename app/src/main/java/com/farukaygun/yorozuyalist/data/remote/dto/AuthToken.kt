package com.farukaygun.yorozuyalist.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AuthToken(
	@SerializedName("access_token")
	val accessToken: String,
	@SerializedName("expires_in")
	val expiresİn: Int,
	@SerializedName("refresh_token")
	val refreshToken: String,
	@SerializedName("token_type")
	val tokenType: String
)