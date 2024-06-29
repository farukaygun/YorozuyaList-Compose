package com.farukaygun.yorozuyalist.presentation.manga_list

import com.farukaygun.yorozuyalist.domain.model.MangaUserList

data class MangaListState(
	val userMangaList: MangaUserList? = null,
	val isLoading: Boolean = false,
	val isLoadingMore: Boolean = false,
	val error: String = ""
)