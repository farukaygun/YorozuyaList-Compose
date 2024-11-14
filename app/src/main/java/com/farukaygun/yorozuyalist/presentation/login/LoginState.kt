package com.farukaygun.yorozuyalist.presentation.login

import com.farukaygun.yorozuyalist.domain.models.AccessToken

data class LoginState(
	val isLoading: Boolean = false,
	val accessToken: AccessToken? = null,
	val error: String = "",
)
