package com.farukaygun.yorozuyalist.presentation.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.farukaygun.yorozuyalist.domain.model.Data
import com.farukaygun.yorozuyalist.domain.model.anime.AnimeSearched

data class SearchState(
	val query: String = "",
	val searchType: SearchType = SearchType.ANIME,
	val animeSearched: AnimeSearched? = null,
	val isLoading: Boolean = false,
	val error: String = ""
)

enum class SearchType {
	ANIME,
	MANGA
}