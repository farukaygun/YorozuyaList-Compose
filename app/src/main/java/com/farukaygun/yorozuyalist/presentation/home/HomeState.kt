package com.farukaygun.yorozuyalist.presentation.home

import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.util.AppError

data class HomeState(
	val animeTodayList: List<Data> = emptyList(),
	val animeSeasonalList: List<Data> = emptyList(),
	val animeSuggestionList: List<Data> = emptyList(),
	val error: AppError? = null,
	val isLoading: Boolean = false
)