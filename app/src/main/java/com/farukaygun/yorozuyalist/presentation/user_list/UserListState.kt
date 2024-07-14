package com.farukaygun.yorozuyalist.presentation.user_list

import com.farukaygun.yorozuyalist.domain.interfaces.MediaList
import com.farukaygun.yorozuyalist.util.UserListType

data class UserListState(
	var userList: MediaList? = null,
	val isLoading: Boolean = false,
	val isLoadingMore: Boolean = false,
	val error: String = "",
	val type: UserListType = UserListType.ANIME_LIST
)