package com.farukaygun.yorozuyalist.presentation.profile

data class ProfileState(
	val profileData: String = "",
	val isLoading: Boolean = false,
	val error: String = ""
)