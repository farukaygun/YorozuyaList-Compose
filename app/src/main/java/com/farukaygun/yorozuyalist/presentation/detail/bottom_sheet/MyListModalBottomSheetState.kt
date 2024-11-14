package com.farukaygun.yorozuyalist.presentation.detail.bottom_sheet

import com.farukaygun.yorozuyalist.domain.interfaces.MediaDetail
import com.farukaygun.yorozuyalist.domain.models.MyListStatus
import com.farukaygun.yorozuyalist.domain.models.enums.MyListMediaStatus

data class MyListModalBottomSheetState(
	val mediaDetail: MediaDetail? = null,
	val myListStatus: MyListStatus? = null,
	var selectedStatus: MyListMediaStatus? = null,
	var episodeCount: Int? = null,
	var chapterCount: Int? = null,
	var volumeCount: Int? = null,
	var score: Int = 0,
	val startDate: String? = "",
	val finishDate: String? = "",
	var tags: String? = "",
	var priority: Int = 0,
	var comments: String = "",
	var checkedRewatchState: Boolean = false,
	var rewatchCount: Int? = null,
	val rewatchValue: Int = 0,
	val isSuccess: Boolean = false,
	val isRemoved: Boolean = false,
	val isLoading: Boolean = false,
	val error: String = ""
)