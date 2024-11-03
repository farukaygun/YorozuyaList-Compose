package com.farukaygun.yorozuyalist.presentation.search

import com.farukaygun.yorozuyalist.domain.models.anime.AnimeSearched

data class SearchState(
	val query: String = "",
	val searchType: SearchType = SearchType.ANIME,
	val animeSearched: AnimeSearched? = null,
	val isLoading: Boolean = false,
	val isLoadingMore: Boolean = false,
	val error: String = ""
)

enum class SearchType {
	ANIME,
	MANGA
}