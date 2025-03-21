package com.farukaygun.yorozuyalist.presentation.login

import com.farukaygun.yorozuyalist.domain.models.AccessToken
import com.farukaygun.yorozuyalist.domain.models.RefreshToken

data class LoginState(
	val accessToken: AccessToken? = null,
	var refreshToken: RefreshToken? = null,
	var isLoggedIn: Boolean = false,
	val isLoading: Boolean = false,
	val error: String = "",
)
