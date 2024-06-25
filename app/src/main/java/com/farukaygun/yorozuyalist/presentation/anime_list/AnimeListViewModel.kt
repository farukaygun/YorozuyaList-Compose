package com.farukaygun.yorozuyalist.presentation.anime_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.farukaygun.yorozuyalist.domain.use_case.AnimeUseCase

class AnimeListViewModel(
	private val animeUseCase: AnimeUseCase
) : ViewModel() {
	private val _state = mutableStateOf(AnimeListState())
	val state: State<AnimeListState> = _state
}