package com.farukaygun.yorozuyalist.presentation.search

import com.farukaygun.yorozuyalist.domain.models.anime.AnimeSearched
import com.farukaygun.yorozuyalist.util.AppError

data class SearchState(
	val query: String = "",
	val searchType: SearchType = SearchType.ANIME,
	val animeSearched: AnimeSearched? = null,
	val isLoading: Boolean = false,
	val isLoadingMore: Boolean = false,
	val error: AppError? = null
)

enum class SearchType {
	ANIME,
	MANGA
}