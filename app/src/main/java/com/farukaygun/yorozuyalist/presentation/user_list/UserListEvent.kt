package com.farukaygun.yorozuyalist.presentation.user_list

sealed class UserListEvent {
	data object InitRequestChain : UserListEvent()
	data object LoadMore : UserListEvent()
}