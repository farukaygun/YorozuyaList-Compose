package com.farukaygun.yorozuyalist.presentation.login

import com.farukaygun.yorozuyalist.domain.model.AuthToken

data class LoginState(
	val isLoading: Boolean = false,
	val authToken: AuthToken? = null,
	val error: String = "",
)
