package com.farukaygun.yorozuyalist.presentation.manga_list

sealed class MangaListEvent {
	data object InitRequestChain : MangaListEvent()
	data object LoadMore: MangaListEvent()
}