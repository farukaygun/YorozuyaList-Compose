package com.farukaygun.yorozuyalist.presentation.grid_list

import com.farukaygun.yorozuyalist.domain.interfaces.MediaList
import com.farukaygun.yorozuyalist.util.enums.GridListType

data class GridListState(
	val gridList: MediaList? = null,
	val isLoading: Boolean = false,
	val isLoadingMore: Boolean = false,
	val error: String = "",
	val type: GridListType = GridListType.SUGGESTED_ANIME_LIST
)