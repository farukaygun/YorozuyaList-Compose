package com.farukaygun.yorozuyalist.presentation.detail.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.interfaces.MediaDetail
import com.farukaygun.yorozuyalist.domain.models.MyListStatus
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeDetail
import com.farukaygun.yorozuyalist.domain.models.enums.MyListMediaStatus
import com.farukaygun.yorozuyalist.domain.models.manga.MangaDetail
import com.farukaygun.yorozuyalist.presentation.composables.DatePickerField
import com.farukaygun.yorozuyalist.presentation.detail.bottom_sheet.MyListBottomSheetEvent
import com.farukaygun.yorozuyalist.presentation.detail.bottom_sheet.MyListModalBottomSheetViewModel
import com.farukaygun.yorozuyalist.util.Extensions.CustomExtensions.formatPriority
import com.farukaygun.yorozuyalist.util.Extensions.CustomExtensions.formatRewatchValue
import com.farukaygun.yorozuyalist.util.Extensions.CustomExtensions.formatScore
import com.farukaygun.yorozuyalist.util.enums.ScreenType
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

val LocalViewModel =
	compositionLocalOf<MyListModalBottomSheetViewModel> { error("MyListModalBottomSheetViewModel not provided") }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyListModalBottomSheet(
	sheetState: SheetState,
	onUpdateSuccess: (myListStatus: MyListStatus?, isRemoved: Boolean) -> Unit,
	onDismiss: () -> Unit,
	viewModel: MyListModalBottomSheetViewModel = koinViewModel(),
	mediaDetail: MediaDetail,
	type: ScreenType
) {
	LaunchedEffect(mediaDetail) {
		viewModel.onEvent(MyListBottomSheetEvent.Init(mediaDetail, type))
	}

	LaunchedEffect(viewModel.state.value.isSuccess) {
		if (viewModel.state.value.isSuccess) {
			viewModel.onEvent(MyListBottomSheetEvent.SetSuccess(false))
			onUpdateSuccess(viewModel.state.value.myListStatus, viewModel.state.value.isRemoved)
		}
	}

	CompositionLocalProvider(LocalViewModel provides viewModel) {
		ModalBottomSheet(sheetState = sheetState, onDismissRequest = onDismiss) {
			Column(
				modifier = Modifier
					.padding(24.dp)
					.verticalScroll(rememberScrollState())
					.imePadding(),
				verticalArrangement = Arrangement.spacedBy(32.dp),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				BottomSheetActionButtons(
					onDismiss = onDismiss,
					onApply = {
						viewModel.onEvent(MyListBottomSheetEvent.UpdateMyListStatusItem(type))
					}
				)
				StatusFilterOptions(type = type)
				ProgressInputField(mediaDetail = mediaDetail)
				MediaScoreSlider()
				DateRangePicker()
				PrioritySlider()
				RewatchCount()
				RewatchValueSlider()
				TagsField()
				NoteInputField()
				DeleteButton(type = type)
			}
		}
	}
}

@Composable
fun BottomSheetActionButtons(
	onDismiss: () -> Unit,
	onApply: () -> Unit,
) {
	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Button(onClick = onDismiss) {
			Text(text = "Cancel")
		}

		Button(onClick = onApply) {
			Text(text = "Apply")
		}
	}
}

@Composable
fun StatusFilterOptions(
	type: ScreenType
) {
	val viewModel = LocalViewModel.current
	val state = viewModel.state.value

	val statusList = remember(type) {
		buildList {
			if (type == ScreenType.ANIME) {
				addAll(listOf(MyListMediaStatus.WATCHING, MyListMediaStatus.PLAN_TO_WATCH))
			} else {
				addAll(listOf(MyListMediaStatus.READING, MyListMediaStatus.PLAN_TO_READ))
			}
			addAll(
				listOf(
					MyListMediaStatus.COMPLETED,
					MyListMediaStatus.ON_HOLD,
					MyListMediaStatus.DROPPED
				)
			)
		}
	}

	fun getStatusIcon(status: MyListMediaStatus, isSelected: Boolean): Int {
		return if (isSelected) {
			R.drawable.close_16px
		} else {
			when (status) {
				MyListMediaStatus.WATCHING, MyListMediaStatus.READING -> R.drawable.play_circle_24px
				MyListMediaStatus.PLAN_TO_WATCH, MyListMediaStatus.PLAN_TO_READ -> R.drawable.schedule_16px
				MyListMediaStatus.COMPLETED -> R.drawable.check_circle_24px
				MyListMediaStatus.ON_HOLD -> R.drawable.pause_circle_24px
				MyListMediaStatus.DROPPED -> R.drawable.delete_24px
			}
		}
	}

	Column {
		Text(
			text = "Status",
			color = MaterialTheme.colorScheme.onBackground,
			style = MaterialTheme.typography.bodySmall
		)

		Row(
			horizontalArrangement = Arrangement.spacedBy(8.dp),
			modifier = Modifier.horizontalScroll(rememberScrollState())
		) {
			statusList.forEach { status ->
				val isSelected = state.selectedStatus?.apiName == status.apiName

				FilterChip(
					onClick = {
						viewModel.onEvent(MyListBottomSheetEvent.SetStatus(newStatus = status))
					},
					label = { Text(status.displayName) },
					selected = isSelected,
					leadingIcon = {
						AnimatedContent(
							targetState = isSelected,
							transitionSpec = { fadeIn() togetherWith fadeOut() },
							label = "",
						) { state ->
							Icon(
								painterResource(id = getStatusIcon(status, state)),
								contentDescription = "Status icon",
							)
						}
					}
				)
			}
		}
	}
}

@Composable
fun ProgressInputField(mediaDetail: MediaDetail) {
	val viewModel = LocalViewModel.current
	val state = viewModel.state.value

	when (mediaDetail) {
		is AnimeDetail -> {
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.Center,
				verticalAlignment = Alignment.CenterVertically
			) {
				IconButton(onClick = {
					viewModel.onEvent(
						MyListBottomSheetEvent.UpdateEpisodeCount(
							(state.episodeCount ?: 0) - 1
						)
					)
				}) {
					Icon(
						painter = painterResource(id = R.drawable.remove_24px),
						contentDescription = "Minus icon"
					)
				}

				OutlinedTextField(
					value = state.episodeCount?.toString() ?: "",
					onValueChange = { input ->
						val newValue = input.toIntOrNull()
						viewModel.onEvent(MyListBottomSheetEvent.UpdateEpisodeCount(newValue))
					},
					suffix = {
						Text(text = "/ ${mediaDetail.numEpisodes}")
					},
					enabled = true,
					readOnly = false,
					label = { Text(text = "Episodes") },
					singleLine = true,
					modifier = Modifier.width(150.dp)
				)

				IconButton(onClick = {
					viewModel.onEvent(
						MyListBottomSheetEvent.UpdateEpisodeCount(
							(state.episodeCount ?: 0) + 1
						)
					)
				}) {
					Icon(
						painter = painterResource(id = R.drawable.add_24px),
						contentDescription = "Plus icon"
					)
				}
			}
		}

		is MangaDetail -> {
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.Center,
				verticalAlignment = Alignment.CenterVertically
			) {
				IconButton(onClick = {
					viewModel.onEvent(
						MyListBottomSheetEvent.UpdateChapterCount(
							(state.chapterCount ?: 0) - 1
						)
					)
				}) {
					Icon(
						painter = painterResource(id = R.drawable.remove_24px),
						contentDescription = "Minus icon"
					)
				}

				OutlinedTextField(
					value = state.chapterCount?.toString() ?: "",
					onValueChange = { input ->
						val newValue = input.toIntOrNull()
						viewModel.onEvent(MyListBottomSheetEvent.UpdateChapterCount(newValue))
					},
					suffix = {
						Text(text = "/ ${mediaDetail.numChapters}")
					},
					label = { Text(text = "Chapters") },
					keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
					singleLine = true,
					modifier = Modifier.width(150.dp)
				)

				IconButton(onClick = {
					viewModel.onEvent(
						MyListBottomSheetEvent.UpdateChapterCount(
							(state.chapterCount ?: 0) + 1
						)
					)
				}) {
					Icon(
						painter = painterResource(id = R.drawable.add_24px),
						contentDescription = "Plus icon"
					)
				}
			}

			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.Center,
				verticalAlignment = Alignment.CenterVertically
			) {
				IconButton(onClick = {
					viewModel.onEvent(
						MyListBottomSheetEvent.UpdateVolumeCount(
							(state.volumeCount ?: 0) - 1
						)
					)
				}) {
					Icon(
						painter = painterResource(id = R.drawable.remove_24px),
						contentDescription = "Minus icon"
					)
				}

				OutlinedTextField(
					value = state.volumeCount?.toString() ?: "",
					onValueChange = { input ->
						val newValue = input.toIntOrNull()
						viewModel.onEvent(MyListBottomSheetEvent.UpdateVolumeCount(newValue))
					},
					suffix = {
						Text(text = "/ ${mediaDetail.numVolumes}")
					},
					label = { Text(text = "Volumes") },
					singleLine = true,
					modifier = Modifier.width(150.dp)
				)

				IconButton(onClick = {
					viewModel.onEvent(
						MyListBottomSheetEvent.UpdateVolumeCount(
							(state.volumeCount ?: 0) + 1
						)
					)
				}) {
					Icon(
						painter = painterResource(id = R.drawable.add_24px),
						contentDescription = "Plus icon"
					)
				}
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaScoreSlider() {
	val viewModel = LocalViewModel.current
	val state by viewModel.state
	val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

	Column {
		Text(
			text = "Score",
			color = MaterialTheme.colorScheme.onBackground,
			style = MaterialTheme.typography.bodySmall
		)

		Slider(
			value = state.score.toFloat(),
			onValueChange = { viewModel.onEvent(MyListBottomSheetEvent.UpdateScore(it.roundToInt())) },
			valueRange = 0f..10f,
			thumb = {
				SliderDefaults.Thumb(
					modifier = Modifier.size(30.dp),
					interactionSource = interactionSource
				)
				Box(
					modifier = Modifier
						.size(30.dp),
					contentAlignment = Alignment.Center
				) {
					Text(
						text = state.score.toString(),
						style = MaterialTheme.typography.bodySmall,
						color = MaterialTheme.colorScheme.onPrimary
					)
				}
			}
		)
		Text(
			text = state.score.formatScore(),
			textAlign = TextAlign.Center,
			style = MaterialTheme.typography.bodySmall,
			modifier = Modifier.fillMaxWidth()
		)
	}
}

@Composable
fun DateRangePicker() {
	val viewModel = LocalViewModel.current
	val state by viewModel.state

	Column {
		Row(
			modifier = Modifier
				.fillMaxWidth(),
			horizontalArrangement = Arrangement.spacedBy(8.dp),
		) {
			DatePickerField(
				selectedDate = state.startDate,
				onDateSelected = { viewModel.onEvent(MyListBottomSheetEvent.UpdateStartDate(it)) },
				onClear = { viewModel.onEvent(MyListBottomSheetEvent.UpdateStartDate("")) },
				label = "Start Date",
				modifier = Modifier.weight(1f)
			)
			DatePickerField(
				selectedDate = state.finishDate,
				onDateSelected = { viewModel.onEvent(MyListBottomSheetEvent.UpdateFinishDate(it)) },
				onClear = { viewModel.onEvent(MyListBottomSheetEvent.UpdateFinishDate("")) },
				label = "End Date",
				modifier = Modifier.weight(1f)
			)
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioritySlider() {
	val viewModel = LocalViewModel.current
	val state by viewModel.state
	val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

	Column {
		Text(
			text = "Priority",
			color = MaterialTheme.colorScheme.onBackground,
			style = MaterialTheme.typography.bodySmall
		)

		Slider(
			value = state.priority.toFloat(),
			onValueChange = { viewModel.onEvent(MyListBottomSheetEvent.UpdatePriority(it.roundToInt())) },
			valueRange = 0f..2f,
			thumb = {
				SliderDefaults.Thumb(
					modifier = Modifier.size(30.dp),
					interactionSource = interactionSource
				)
				Box(
					modifier = Modifier
						.size(30.dp),
					contentAlignment = Alignment.Center
				) {
					Text(
						text = state.priority.toString(),
						style = MaterialTheme.typography.bodySmall,
						color = MaterialTheme.colorScheme.onPrimary
					)
				}
			}
		)
		Text(
			text = state.priority.formatPriority(),
			textAlign = TextAlign.Center,
			style = MaterialTheme.typography.bodySmall,
			modifier = Modifier.fillMaxWidth()
		)
	}
}

@Composable
fun RewatchCount() {
	val viewModel = LocalViewModel.current
	val state by viewModel.state

	Column(horizontalAlignment = Alignment.CenterHorizontally) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.Center,
			verticalAlignment = Alignment.CenterVertically
		) {
			IconButton(
				onClick = {
					viewModel.onEvent(
						MyListBottomSheetEvent.UpdateRewatchCount(
							(state.rewatchCount ?: 0) - 1
						)
					)
				}
			) {
				Icon(
					painter = painterResource(id = R.drawable.remove_24px),
					contentDescription = "Minus icon"
				)
			}

			OutlinedTextField(
				value = state.rewatchCount?.toString() ?: "",
				onValueChange = { input ->
					val newValue = input.toIntOrNull()
					viewModel.onEvent(MyListBottomSheetEvent.UpdateRewatchCount(newValue))
				},
				label = { Text(text = "Rewatch Count") },
				keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
				singleLine = true,
				modifier = Modifier.width(150.dp)
			)

			IconButton(
				onClick = {
					viewModel.onEvent(
						MyListBottomSheetEvent.UpdateRewatchCount(
							(state.rewatchCount ?: 0) + 1
						)
					)
				}
			) {
				Icon(
					painter = painterResource(id = R.drawable.add_24px),
					contentDescription = "Plus icon"
				)
			}
		}

		Row(
			verticalAlignment = Alignment.CenterVertically
		) {
			Checkbox(
				modifier = Modifier.scale(1f),
				checked = state.checkedRewatchState,
				onCheckedChange = { viewModel.onEvent(MyListBottomSheetEvent.ToggleRewatchState) }
			)
			Text(
				text = "Rewatching",
				style = MaterialTheme.typography.bodySmall,
			)
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewatchValueSlider() {
	val viewModel = LocalViewModel.current
	val state by viewModel.state
	val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

	Column {
		Text(
			text = "Rewatch Value",
			color = MaterialTheme.colorScheme.onBackground,
			style = MaterialTheme.typography.bodySmall
		)

		Slider(
			value = state.rewatchValue.toFloat(),
			onValueChange = { viewModel.onEvent(MyListBottomSheetEvent.UpdateRewatchValue(it.roundToInt())) },
			valueRange = 0f..5f,
			thumb = {
				SliderDefaults.Thumb(
					modifier = Modifier.size(30.dp),
					interactionSource = interactionSource
				)
				Box(
					modifier = Modifier
						.size(30.dp),
					contentAlignment = Alignment.Center
				) {
					Text(
						text = state.rewatchValue.toString(),
						style = MaterialTheme.typography.bodySmall,
						color = MaterialTheme.colorScheme.onPrimary
					)
				}
			}
		)
		Text(
			text = state.rewatchValue.formatRewatchValue(),
			textAlign = TextAlign.Center,
			style = MaterialTheme.typography.bodySmall,
			modifier = Modifier.fillMaxWidth()
		)
	}
}

@Composable
fun TagsField() {
	val viewModel = LocalViewModel.current
	val state by viewModel.state

	OutlinedTextField(
		value = state.tags.toString(),
		onValueChange = {
			if (it.length <= 500) viewModel.onEvent(MyListBottomSheetEvent.UpdateTags(it))
		},
		label = { Text(text = "Tags") },
		modifier = Modifier.fillMaxWidth(),
		singleLine = true
	)
}

@Composable
fun NoteInputField() {
	val viewModel = LocalViewModel.current
	val state by viewModel.state

	OutlinedTextField(
		value = state.comments,
		onValueChange = { viewModel.onEvent(MyListBottomSheetEvent.UpdateComments(it)) },
		label = { Text(text = "Notes") },
		modifier = Modifier.fillMaxWidth(),
		singleLine = false
	)
}

@Composable
fun DeleteButton(
	type: ScreenType
) {
	val viewModel = LocalViewModel.current

	Button(
		onClick = { viewModel.onEvent(MyListBottomSheetEvent.DeleteMyListStatusItem(type)) },
		modifier = Modifier.fillMaxWidth(),
		colors = ButtonDefaults.buttonColors(
			containerColor = MaterialTheme.colorScheme.error,
			contentColor = MaterialTheme.colorScheme.onError
		)
	) {
		Text(text = "Delete")
	}
}