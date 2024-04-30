package com.farukaygun.yorozuyalist.presentation.home

import com.farukaygun.yorozuyalist.domain.model.Data
import com.farukaygun.yorozuyalist.domain.model.RefreshToken

data class HomeState(
	val animeTodayList: List<Data> = emptyList(),
	val animeSeasonalList: List<Data> = emptyList(),
	val animeSuggestionList: List<Data> = emptyList(),
	var refreshToken: RefreshToken? = null,
	var isLoggedIn: Boolean = false,
	val isLoading: Boolean = false,
	val error: String = ""
)