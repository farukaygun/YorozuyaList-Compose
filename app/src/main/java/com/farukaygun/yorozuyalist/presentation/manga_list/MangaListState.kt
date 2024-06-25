package com.farukaygun.yorozuyalist.presentation.manga_list

import com.farukaygun.yorozuyalist.domain.model.Data

data class MangaListState(
	val userMangaList: List<Data> = emptyList(),
	val isLoading: Boolean = false,
	val error: String = ""
)