package com.farukaygun.yorozuyalist.presentation.home

import com.farukaygun.yorozuyalist.domain.models.Data

data class HomeState(
	val animeTodayList: List<Data> = emptyList(),
	val animeSeasonalList: List<Data> = emptyList(),
	val animeSuggestionList: List<Data> = emptyList(),
	val error: String = "",
	val isLoading: Boolean = false,
)