package com.farukaygun.yorozuyalist.presentation.anime_list

import com.farukaygun.yorozuyalist.presentation.search.SearchEvent

sealed class AnimeListEvent {
	data object InitRequestChain: AnimeListEvent()
	data object LoadMore: AnimeListEvent()
}