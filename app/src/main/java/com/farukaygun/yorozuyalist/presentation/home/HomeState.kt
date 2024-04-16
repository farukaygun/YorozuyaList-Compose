package com.farukaygun.yorozuyalist.presentation.home

import com.farukaygun.yorozuyalist.domain.model.RefreshToken

data class HomeState(
	val refreshToken: RefreshToken? = null,
	val isLoading: Boolean = false,
	val error: String = ""
)