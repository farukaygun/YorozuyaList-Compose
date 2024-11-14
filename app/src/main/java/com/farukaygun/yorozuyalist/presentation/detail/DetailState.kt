package com.farukaygun.yorozuyalist.presentation.detail

import com.farukaygun.yorozuyalist.domain.interfaces.MediaDetail
import com.farukaygun.yorozuyalist.util.ScreenType

data class DetailState(
	val detail: MediaDetail? = null,
	val isLoading: Boolean = false,
	val error: String = "",
	val type: ScreenType = ScreenType.ANIME
)