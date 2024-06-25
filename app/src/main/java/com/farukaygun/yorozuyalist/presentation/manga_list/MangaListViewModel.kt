package com.farukaygun.yorozuyalist.presentation.manga_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.farukaygun.yorozuyalist.domain.use_case.MangaUseCase

class MangaListViewModel(
	private val mangaUseCase: MangaUseCase
) : ViewModel() {
	private val _state = mutableStateOf(MangaListState())
	val state: State<MangaListState> = _state
}