package com.farukaygun.yorozuyalist.presentation.login

import com.farukaygun.yorozuyalist.domain.model.AuthToken
import kotlinx.coroutines.flow.emptyFlow

data class LoginState(
	val isLoading: Boolean = false,
	val authToken: AuthToken = AuthToken("", 0, "", ""),
	val error: String = ""
)
