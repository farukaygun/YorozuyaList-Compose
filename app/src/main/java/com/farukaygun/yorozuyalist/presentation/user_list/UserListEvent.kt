package com.farukaygun.yorozuyalist.presentation.user_list

import com.farukaygun.yorozuyalist.domain.models.enums.MyListMediaStatus

sealed class UserListEvent {
	data object InitRequestChain : UserListEvent()
	data object LoadMore : UserListEvent()
	data class FilterList(val filter: MyListMediaStatus?): UserListEvent()
}