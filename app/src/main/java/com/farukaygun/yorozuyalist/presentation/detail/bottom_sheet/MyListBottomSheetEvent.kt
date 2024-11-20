package com.farukaygun.yorozuyalist.presentation.detail.bottom_sheet

import com.farukaygun.yorozuyalist.domain.interfaces.MediaDetail
import com.farukaygun.yorozuyalist.domain.models.enums.MyListMediaStatus
import com.farukaygun.yorozuyalist.util.enums.ScreenType

sealed class MyListBottomSheetEvent {
	data class Init(val detail: MediaDetail, val type: ScreenType) : MyListBottomSheetEvent()
	data class SetStatus(val newStatus: MyListMediaStatus) : MyListBottomSheetEvent()
	data class UpdateEpisodeCount(val newValue: Int?) : MyListBottomSheetEvent()
	data class UpdateChapterCount(val newValue: Int?) : MyListBottomSheetEvent()
	data class UpdateVolumeCount(val newValue: Int?) : MyListBottomSheetEvent()
	data class UpdateScore(val newScore: Int) : MyListBottomSheetEvent()
	data class UpdateStartDate(val newStartDate: String) : MyListBottomSheetEvent()
	data class UpdateFinishDate(val newFinishDate: String) : MyListBottomSheetEvent()
	data class UpdateTags(val newTags: String) : MyListBottomSheetEvent()
	data class UpdatePriority(val newPriority: Int) : MyListBottomSheetEvent()
	data object ToggleRewatchState : MyListBottomSheetEvent()
	data class UpdateRewatchCount(val newValue: Int?) : MyListBottomSheetEvent()
	data class UpdateRewatchValue(val newValue: Int) : MyListBottomSheetEvent()
	data class UpdateComments(val newNote: String) : MyListBottomSheetEvent()
	data class UpdateMyListStatusItem(val type: ScreenType) : MyListBottomSheetEvent()
	data class DeleteMyListStatusItem(val type: ScreenType) : MyListBottomSheetEvent()
	data class SetSuccess(val isSuccess: Boolean) : MyListBottomSheetEvent()
}