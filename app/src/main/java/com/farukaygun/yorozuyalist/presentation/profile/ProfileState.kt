package com.farukaygun.yorozuyalist.presentation.profile

import com.farukaygun.yorozuyalist.domain.models.Stat
import com.farukaygun.yorozuyalist.domain.models.user.User

data class ProfileState(
	val profileData: User? = null,
	val animeStats: List<Stat> = emptyList(),
	val isLoading: Boolean = false,
	val error: String = ""
)