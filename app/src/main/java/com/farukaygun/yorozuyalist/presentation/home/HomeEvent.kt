package com.farukaygun.yorozuyalist.presentation.home

sealed class HomeEvent {
	data object InitRequestChain : HomeEvent()
}