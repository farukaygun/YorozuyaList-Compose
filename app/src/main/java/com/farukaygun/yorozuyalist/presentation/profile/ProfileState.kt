package com.farukaygun.yorozuyalist.presentation.profile

import com.farukaygun.yorozuyalist.domain.models.Stat
import com.farukaygun.yorozuyalist.domain.models.user.User
import com.farukaygun.yorozuyalist.util.AppError

data class ProfileState(
	val profileData: User? = null,
	val animeStats: List<Stat> = emptyList(),
	val isLoading: Boolean = false,
	val error: AppError? = null
)