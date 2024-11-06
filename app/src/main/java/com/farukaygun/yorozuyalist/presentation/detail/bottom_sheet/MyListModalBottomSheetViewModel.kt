package com.farukaygun.yorozuyalist.presentation.detail.bottom_sheet

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.interfaces.MediaDetail
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeDetail
import com.farukaygun.yorozuyalist.domain.models.enums.MyListMediaStatus
import com.farukaygun.yorozuyalist.domain.models.manga.MangaDetail
import com.farukaygun.yorozuyalist.domain.use_case.AnimeUseCase
import com.farukaygun.yorozuyalist.domain.use_case.MangaUseCase
import com.farukaygun.yorozuyalist.presentation.base.BaseViewModel
import com.farukaygun.yorozuyalist.util.Extensions.CustomExtensions.formatDate
import com.farukaygun.yorozuyalist.util.Extensions.CustomExtensions.formatToISODate
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.ScreenType
import com.farukaygun.yorozuyalist.util.StringValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MyListModalBottomSheetViewModel(
	private val animeUseCase: AnimeUseCase,
	private val mangaUseCase: MangaUseCase
) : BaseViewModel<MyListModalBottomSheetState>() {
	override val _state = mutableStateOf(MyListModalBottomSheetState())

	private fun init(mediaDetail: MediaDetail) {
		val myListStatus = mediaDetail.myListStatus
		_state.value = _state.value.copy(
			isSuccess = false,
			isRemoved = false,
			myListStatus = myListStatus,
			episodeCount = if (mediaDetail is AnimeDetail) myListStatus?.numEpisodesWatched
				?: 0 else _state.value.episodeCount,
			chapterCount = if (mediaDetail is MangaDetail) myListStatus?.numChaptersRead
				?: 0 else _state.value.chapterCount,
			volumeCount = if (mediaDetail is MangaDetail) myListStatus?.numVolumesRead
				?: 0 else _state.value.volumeCount,
			mediaDetail = mediaDetail,
			selectedStatus = myListStatus?.status,
			score = myListStatus?.score ?: 0,
			startDate = myListStatus?.startDate?.formatDate(),
			finishDate = myListStatus?.finishDate?.formatDate(),
			tags = myListStatus?.tags?.joinToString() ?: "",
			priority = myListStatus?.priority ?: 0,
			comments = myListStatus?.comments ?: "",
			checkedRewatchState = myListStatus?.isRewatching == true,
			rewatchCount = myListStatus?.numTimesRewatched ?: 0,
			rewatchValue = myListStatus?.rewatchValue ?: 0
		)
	}

	private fun setStatus(newStatus: MyListMediaStatus) {
		val mediaDetail = _state.value.mediaDetail
		val completedStatus = newStatus == MyListMediaStatus.COMPLETED

		_state.value = _state.value.copy(
			selectedStatus = if (_state.value.selectedStatus?.formatForApi() == newStatus.formatForApi()) null else newStatus,
			episodeCount = if (mediaDetail is AnimeDetail && completedStatus) mediaDetail.numEpisodes else _state.value.episodeCount,
			chapterCount = if (mediaDetail is MangaDetail && completedStatus) mediaDetail.numChapters else _state.value.chapterCount,
			volumeCount = if (mediaDetail is MangaDetail && completedStatus) mediaDetail.numVolumes else _state.value.volumeCount
		)
	}

	private fun updateEpisodeCount(newValue: Int?) {
		if (!isValueValid(newValue, (_state.value.mediaDetail as AnimeDetail).numEpisodes)) return

		_state.value = _state.value.copy(
			episodeCount = newValue
		)
	}

	private fun updateChapterCount(newValue: Int?) {
		if (!isValueValid(newValue, (_state.value.mediaDetail as MangaDetail).numChapters)) return

		_state.value = _state.value.copy(
			chapterCount = newValue
		)
	}

	private fun updateVolumeCount(newValue: Int?) {
		if (!isValueValid(newValue, (_state.value.mediaDetail as MangaDetail).numVolumes)) return

		_state.value = _state.value.copy(
			volumeCount = newValue
		)
	}

	private fun isValueValid(value: Int?, limit: Int?): Boolean {
		if (value == null || value == 0) return true
		if (value < 0) return false
		if (limit == null || limit <= 0) return true
		return value <= limit
	}

	private fun updateScore(newScore: Int) {
		_state.value = _state.value.copy(score = newScore.coerceAtMost(10))
	}

	private fun updateStartDate(newStartDate: String) {
		_state.value = _state.value.copy(startDate = newStartDate)
	}

	private fun updateFinishDate(newEndDate: String) {
		_state.value = _state.value.copy(finishDate = newEndDate)
	}

	private fun updateTags(newTags: String) {
		_state.value = _state.value.copy(tags = newTags)
	}

	private fun updatePriority(newPriority: Int) {
		_state.value = _state.value.copy(
			priority = newPriority
				.coerceAtLeast(0)
				.coerceAtMost(2)
		)
	}

	private fun toggleRewatchState() {
		_state.value = _state.value.copy(checkedRewatchState = !_state.value.checkedRewatchState)
	}

	private fun updateRewatchCount(newValue: Int?) {
		if (newValue != null && newValue !in 0..5) return

		_state.value = _state.value.copy(
			rewatchCount = newValue
		)
	}

	private fun updateRewatchValue(newValue: Int) {
		_state.value = _state.value.copy(
			rewatchValue = newValue
				.coerceAtLeast(0)
				.coerceAtMost(55)
		)
	}

	private fun updateComments(newNote: String) {
		_state.value = _state.value.copy(comments = newNote)
	}

	private fun updateMyListItem(type: ScreenType) {
		_state.value.run {
			val id = mediaDetail?.id ?: return

			val selectedStatus = selectedStatus.takeIf { it != myListStatus?.status }
			val score = score.takeIf { it != myListStatus?.score }
			val startDate = startDate.takeIf { it != myListStatus?.startDate }
			val finishDate = finishDate.takeIf { it != myListStatus?.finishDate }
			val tags = tags?.split(",").takeIf { it != myListStatus?.tags }
			val priority = priority.takeIf { it != myListStatus?.priority }
			val isRewatching = checkedRewatchState.takeIf { it != myListStatus?.isRewatching }
			val rewatchCount = rewatchCount.takeIf { it != myListStatus?.numTimesRewatched }
			val rewatchValue = rewatchValue.takeIf { it != myListStatus?.rewatchValue }
			val comments = comments.takeIf { it != myListStatus?.comments }

			val formattedStatus = selectedStatus?.formatForApi()
			val formattedStartDate = startDate?.formatToISODate()
			val formattedFinishDate = finishDate?.formatToISODate()
			val formattedTags = tags?.joinToString()

			val updateMyListStatusItem = when (type) {
				ScreenType.ANIME -> {
					val episodeCount =
						episodeCount.takeIf { it != myListStatus?.numEpisodesWatched }

					animeUseCase.executeUpdateMyAnimeListItem(
						id = id,
						status = formattedStatus,
						episodeCount = episodeCount,
						score = score,
						startDate = formattedStartDate,
						finishDate = formattedFinishDate,
						tags = formattedTags,
						priority = priority,
						isRewatching = isRewatching,
						rewatchCount = rewatchCount,
						rewatchValue = rewatchValue,
						comments = comments
					)
				}

				ScreenType.MANGA -> {
					val chapterCount = chapterCount.takeIf { it != myListStatus?.numChaptersRead }
					val volumeCount = volumeCount.takeIf { it != myListStatus?.numVolumesRead }

					mangaUseCase.executeUpdateMyMangaListItem(
						id = id,
						status = formattedStatus,
						chapterCount = chapterCount,
						volumeCount = volumeCount,
						score = score,
						startDate = formattedStartDate,
						finishDate = formattedFinishDate,
						tags = formattedTags,
						priority = priority,
						isRereading = isRewatching,
						rereadCount = rewatchCount,
						rereadValue = rewatchValue,
						comments = comments
					)
				}
			}


			jobs += updateMyListStatusItem
				.flowOn(Dispatchers.IO)
				.onEach {
					when (it) {
						is Resource.Success -> {
							_state.value = _state.value.copy(
								myListStatus = it.data,
								isSuccess = true,
								isLoading = false,
								error = ""
							)
						}

						is Resource.Error -> {
							_state.value = _state.value.copy(
								isSuccess = false,
								isLoading = false,
								error = it.message
									?: StringValue.StringResource(R.string.error_fetching)
										.toString(),
							)
						}

						is Resource.Loading -> {
							_state.value = _state.value.copy(
								isLoading = true,
								isSuccess = false
							)
						}
					}
				}.launchIn(viewModelScope)
		}
	}

	private fun deleteMyListStatusItemEntry(type: ScreenType) {
		_state.value.run {
			val id = mediaDetail?.id ?: return
			val deleteMyListStatusItem = when (type) {
				ScreenType.ANIME -> animeUseCase.executeDeleteMyAnimeListItem(id)
				ScreenType.MANGA -> mangaUseCase.executeDeleteMyMangaListItem(id)
			}

			jobs += deleteMyListStatusItem
				.flowOn(Dispatchers.IO)
				.onEach {
					when (it) {
						is Resource.Success -> {
							_state.value = _state.value.copy(
								myListStatus = null,
								isSuccess = true,
								isRemoved = true,
								isLoading = false,
								error = ""
							)
						}

						is Resource.Error -> {
							_state.value = _state.value.copy(
								isSuccess = false,
								isRemoved = false,
								isLoading = false,
								error = it.message
									?: StringValue.StringResource(R.string.error_fetching)
										.toString(),
							)
						}

						is Resource.Loading -> {
							_state.value = _state.value.copy(
								isLoading = true,
								isRemoved = false
							)
						}
					}
				}.launchIn(viewModelScope)
		}
	}

	private fun setSuccess(isSuccess: Boolean) {
		_state.value = _state.value.copy(isSuccess = isSuccess)
	}

	fun onEvent(event: MyListBottomSheetEvent) {
		when (event) {
			is MyListBottomSheetEvent.Init -> init(event.detail)
			is MyListBottomSheetEvent.SetStatus -> setStatus(event.newStatus)
			is MyListBottomSheetEvent.UpdateEpisodeCount -> updateEpisodeCount(event.newValue)
			is MyListBottomSheetEvent.UpdateChapterCount -> updateChapterCount(event.newValue)
			is MyListBottomSheetEvent.UpdateVolumeCount -> updateVolumeCount(event.newValue)
			is MyListBottomSheetEvent.UpdateScore -> updateScore(event.newScore)
			is MyListBottomSheetEvent.UpdateStartDate -> updateStartDate(event.newStartDate)
			is MyListBottomSheetEvent.UpdateFinishDate -> updateFinishDate(event.newFinishDate)
			is MyListBottomSheetEvent.UpdateTags -> updateTags(event.newTags)
			is MyListBottomSheetEvent.UpdatePriority -> updatePriority(event.newPriority)
			is MyListBottomSheetEvent.ToggleRewatchState -> toggleRewatchState()
			is MyListBottomSheetEvent.UpdateRewatchCount -> updateRewatchCount(event.newValue)
			is MyListBottomSheetEvent.UpdateRewatchValue -> updateRewatchValue(event.newValue)
			is MyListBottomSheetEvent.UpdateComments -> updateComments(event.newNote)
			is MyListBottomSheetEvent.UpdateMyListStatusItem -> updateMyListItem(event.type)
			is MyListBottomSheetEvent.DeleteMyListStatusItem -> deleteMyListStatusItemEntry(event.type)
			is MyListBottomSheetEvent.SetSuccess -> setSuccess(event.isSuccess)
		}
	}
}