package com.farukaygun.yorozuyalist.presentation.profile

sealed class ProfileEvent {
	data object InitRequestChain : ProfileEvent()
}