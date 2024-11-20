package com.farukaygun.yorozuyalist.presentation.user_list

import com.farukaygun.yorozuyalist.domain.interfaces.MediaList
import com.farukaygun.yorozuyalist.util.enums.ScreenType

data class UserListState(
	val userList: MediaList? = null,
	val isLoading: Boolean = false,
	val isLoadingMore: Boolean = false,
	val error: String = "",
	val type: ScreenType = ScreenType.ANIME,
	val filter: String? = null
)