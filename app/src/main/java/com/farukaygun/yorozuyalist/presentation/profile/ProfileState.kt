package com.farukaygun.yorozuyalist.presentation.profile

import com.farukaygun.yorozuyalist.domain.model.Stat
import com.farukaygun.yorozuyalist.domain.model.user.User

data class ProfileState(
	val profileData: User? = null,
	val animeStats: List<Stat> = emptyList(),
	val isLoading: Boolean = false,
	val error: String = ""
)