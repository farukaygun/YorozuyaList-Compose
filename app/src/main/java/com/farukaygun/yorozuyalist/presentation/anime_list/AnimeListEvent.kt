package com.farukaygun.yorozuyalist.presentation.anime_list

sealed class AnimeListEvent {
	data object InitRequestChain: AnimeListEvent()
	data object LoadMore: AnimeListEvent()
}