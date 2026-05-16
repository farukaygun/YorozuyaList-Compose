package com.farukaygun.yorozuyalist.presentation.detail

import com.farukaygun.yorozuyalist.domain.interfaces.MediaDetail
import com.farukaygun.yorozuyalist.util.AppError
import com.farukaygun.yorozuyalist.util.enums.ScreenType

data class DetailState(
	val detail: MediaDetail? = null,
	val isLoading: Boolean = false,
	val error: AppError? = null,
	val type: ScreenType = ScreenType.ANIME
)