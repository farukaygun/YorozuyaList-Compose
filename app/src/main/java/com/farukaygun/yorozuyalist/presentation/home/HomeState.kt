package com.farukaygun.yorozuyalist.presentation.home

import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.RefreshToken

data class HomeState(
	val animeTodayList: List<Data> = emptyList(),
	val animeSeasonalList: List<Data> = emptyList(),
	val animeSuggestionList: List<Data> = emptyList(),
	var refreshToken: RefreshToken? = null,
	var isLoggedIn: Boolean = false,
	var isLoading: Boolean = false,
	val error: String = ""
)