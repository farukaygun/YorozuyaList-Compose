package com.farukaygun.yorozuyalist.presentation.grid_list

sealed class GridListEvent {
	data object InitRequestChain : GridListEvent()
	data object LoadMore : GridListEvent()
}