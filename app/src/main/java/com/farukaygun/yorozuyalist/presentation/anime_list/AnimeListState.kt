package com.farukaygun.yorozuyalist.presentation.anime_list

import com.farukaygun.yorozuyalist.domain.model.anime.AnimeUserList

data class AnimeListState(
	val userAnimeList: AnimeUserList? = null,
	val isLoading: Boolean = false,
	val isLoadingMore: Boolean = false,
	val error: String = ""
)