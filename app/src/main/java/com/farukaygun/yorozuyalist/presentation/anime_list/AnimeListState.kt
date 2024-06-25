package com.farukaygun.yorozuyalist.presentation.anime_list

import com.farukaygun.yorozuyalist.domain.model.Data

data class AnimeListState(
	val userAnimeList: List<Data> = emptyList(),
	val isLoading: Boolean = false,
	val error: String = ""
)