package com.farukaygun.yorozuyalist.presentation.user_list

import com.farukaygun.yorozuyalist.domain.model.UserList
import com.farukaygun.yorozuyalist.util.ListType

data class UserListState(
	val userList: UserList? = null,
	val isLoading: Boolean = false,
	val isLoadingMore: Boolean = false,
	val error: String = "",
	val type: ListType = ListType.ANIME_LIST
)