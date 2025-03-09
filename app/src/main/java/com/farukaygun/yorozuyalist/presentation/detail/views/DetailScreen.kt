package com.farukaygun.yorozuyalist.presentation.detail.views

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.data.di.apiServiceModule
import com.farukaygun.yorozuyalist.data.di.repositoryModule
import com.farukaygun.yorozuyalist.data.di.useCaseModule
import com.farukaygun.yorozuyalist.data.di.viewModelModule
import com.farukaygun.yorozuyalist.domain.interfaces.MediaDetail
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeDetail
import com.farukaygun.yorozuyalist.domain.models.enums.MyListMediaStatus
import com.farukaygun.yorozuyalist.domain.models.manga.MangaDetail
import com.farukaygun.yorozuyalist.presentation.detail.DetailEvent
import com.farukaygun.yorozuyalist.presentation.detail.DetailViewModel
import com.farukaygun.yorozuyalist.presentation.detail.views.shimmer_effect.ShimmerEffectDetailScreen
import com.farukaygun.yorozuyalist.util.Extensions.CustomExtensions.formatDate
import com.farukaygun.yorozuyalist.util.Extensions.CustomExtensions.localizeNumber
import com.farukaygun.yorozuyalist.util.Extensions.ModelExtensions.episodeDurationLocalized
import com.farukaygun.yorozuyalist.util.Extensions.ModelExtensions.formatBroadcast
import com.farukaygun.yorozuyalist.util.Extensions.ModelExtensions.formatMediaDuration
import com.farukaygun.yorozuyalist.util.Extensions.ModelExtensions.formatStartSeason
import com.farukaygun.yorozuyalist.util.enums.ScreenType
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

val LocalMediaDetail = compositionLocalOf<MediaDetail> { error("MediaDetail not provided") }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
	navController: NavController,
	viewModel: DetailViewModel = koinViewModel(),
	type: String
) {
	val state = viewModel.state.value
	val sheetState = rememberModalBottomSheetState()
	val showBottomSheet = remember { mutableStateOf(false) }
	val scope = rememberCoroutineScope()

	LaunchedEffect(sheetState.isVisible) {
		if (!sheetState.isVisible) {
			showBottomSheet.value = false
		}
	}

	if (state.detail != null && !state.isLoading) {
		CompositionLocalProvider(LocalMediaDetail provides state.detail) {
			Box(
				contentAlignment = Alignment.BottomEnd,
			) {
				Column(
					modifier = Modifier
						.padding(16.dp)
						.statusBarsPadding()
						.verticalScroll(rememberScrollState()),
					verticalArrangement = Arrangement.spacedBy(16.dp)
				) {
					MediaInfo()
					GenresSection()
					SynopsisSection()
					StatisticsSection()
					MoreInfoSection()
					RelatedSection(navController = navController)
					RecommendationSection(
						navController = navController,
						type = type
					)
				}

				MyListFloatingActionButton(state.detail, showBottomSheet)
			}

			AnimatedVisibility(
				visible = showBottomSheet.value,
				enter = slideInVertically(
					initialOffsetY = { it },
					animationSpec = tween(350)
				),
				exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(350))
			) {
				MyListModalBottomSheet(
					sheetState = sheetState,
					onUpdateSuccess = { status, isRemoved ->
						scope.launch {
							sheetState.hide()
						}
						viewModel.onEvent(DetailEvent.OnMyListStatusChanged(status, isRemoved))
					},
					onDismiss = {
						scope.launch {
							sheetState.hide()
						}
					},
					mediaDetail = state.detail,
					type = ScreenType.valueOf(type),
				)
			}
		}
	} else {
		ShimmerEffectDetailScreen()
	}

	if (state.error.isNotEmpty())
		Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_SHORT).show()
}

@Composable
private fun MediaInfo() {
	val mediaDetail = LocalMediaDetail.current

	Row(
		modifier = Modifier.padding(bottom = 8.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(16.dp)
	) {
		SubcomposeAsyncImage(
			model = ImageRequest.Builder(LocalContext.current)
				.data(LocalMediaDetail.current.mainPicture?.medium)
				.crossfade(true)
				.crossfade(350)
				.build(),
			loading = {
				Column(
					verticalArrangement = Arrangement.Center,
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					CircularProgressIndicator()
				}
			},
			error = {
				Icon(
					painter = painterResource(id = R.drawable.broken_image_24px),
					contentDescription = "Error icon",
				)
			},
			contentDescription = stringResource(R.string.media_image),
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.clip(RoundedCornerShape(10.dp))
				.size(100.dp, 150.dp)
		)

		Column(
			verticalArrangement = Arrangement.spacedBy(space = 8.dp)
		) {
			Row(
				verticalAlignment = Alignment.CenterVertically
			) {
				Icon(
					painter = painterResource(id = R.drawable.movie_24px),
					contentDescription = "Media type icon",
					modifier = Modifier.padding(end = 8.dp)
				)

				Text(
					text = mediaDetail.title,
					textAlign = TextAlign.Center,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					color = MaterialTheme.colorScheme.onBackground,
					style = MaterialTheme.typography.titleMedium
				)
			}

			Row(
				verticalAlignment = Alignment.CenterVertically
			) {
				Icon(
					painter = painterResource(id = R.drawable.rss_feed_24px),
					contentDescription = "Media type icon",
					modifier = Modifier.padding(end = 8.dp)
				)

				Text(
					text = mediaDetail.status?.displayName ?: "N/A",
					textAlign = TextAlign.Center,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					color = MaterialTheme.colorScheme.onBackground,
					style = MaterialTheme.typography.bodyMedium
				)
			}

			Row(
				verticalAlignment = Alignment.CenterVertically
			) {
				Icon(
					painter = painterResource(id = R.drawable.timer_24px),
					contentDescription = "Media type icon",
					modifier = Modifier.padding(end = 8.dp)
				)

				Text(
					text = mediaDetail.formatMediaDuration(),
					textAlign = TextAlign.Center,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					color = MaterialTheme.colorScheme.onBackground,
					style = MaterialTheme.typography.bodyMedium
				)
			}

			Row(
				verticalAlignment = Alignment.CenterVertically
			) {
				Icon(
					painter = painterResource(id = R.drawable.grade_24px),
					contentDescription = "Media type icon",
					modifier = Modifier.padding(end = 8.dp)
				)

				Text(
					text = mediaDetail.mean.toString(),
					textAlign = TextAlign.Center,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					color = MaterialTheme.colorScheme.onBackground,
					style = MaterialTheme.typography.bodyMedium
				)
			}
		}
	}
}

@Composable
fun GenresSection() {
	val mediaDetail = LocalMediaDetail.current

	Row(
		modifier = Modifier
			.horizontalScroll(rememberScrollState()),
		horizontalArrangement = Arrangement.spacedBy(8.dp)
	) {
		mediaDetail.genres.forEach { genre ->
			AssistChip(onClick = {}, label = {
				Text(
					text = genre.name,
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.onSurface
				)
			})
		}
	}
}

@Composable
fun SynopsisSection() {
	val mediaDetail = LocalMediaDetail.current
	val maxLinesCollapsed = 5
	var expanded by remember { mutableStateOf(false) }

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(bottom = 16.dp)
			.animateContentSize()
	) {
		Text(
			text = mediaDetail.synopsis,
			style = MaterialTheme.typography.bodyMedium,
			color = MaterialTheme.colorScheme.onBackground,
			modifier = Modifier.padding(vertical = 8.dp),
			maxLines = if (expanded) Int.MAX_VALUE else maxLinesCollapsed
		)

		TextButton(
			onClick = { expanded = !expanded },
			modifier = Modifier.align(Alignment.End)
		) {
			Text(text = if (expanded) "Read Less" else "Read More")
		}
	}
}

@Composable
fun StatisticsSection() {
	val mediaDetail = LocalMediaDetail.current

	Column(
		modifier = Modifier.padding(bottom = 16.dp)
	) {
		Text(
			text = "Statistics",
			style = MaterialTheme.typography.titleMedium,
			color = MaterialTheme.colorScheme.onBackground,
			modifier = Modifier.padding(bottom = 8.dp)
		)
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceEvenly,
			verticalAlignment = Alignment.CenterVertically
		) {

			TextIconVertical(
				text = mediaDetail.rank.toString(),
				icon = R.drawable.bar_chart_24px,
			)
			VerticalDivider(modifier = Modifier.height(32.dp))

			TextIconVertical(
				text = mediaDetail.numScoringUsers.localizeNumber(),
				icon = R.drawable.thumbs_up_down_24px,
			)
			VerticalDivider(modifier = Modifier.height(32.dp))

			TextIconVertical(
				text = mediaDetail.numListUsers.localizeNumber(),
				icon = R.drawable.group_24px,
			)
			VerticalDivider(modifier = Modifier.height(32.dp))

			TextIconVertical(
				text = "#${mediaDetail.popularity.localizeNumber()}",
				icon = R.drawable.trending_up_24px,
			)
		}
	}
}

@Composable
fun MoreInfoSection() {
	val mediaDetail = LocalMediaDetail.current

	Column(
		modifier = Modifier.padding(bottom = 16.dp)
	) {
		Text(
			text = "More Info",
			style = MaterialTheme.typography.titleMedium,
			color = MaterialTheme.colorScheme.onBackground,
			modifier = Modifier.padding(bottom = 8.dp)
		)
		if (mediaDetail is AnimeDetail) {
			MediaInfoView(
				title = "Duration",
				info = mediaDetail.episodeDurationLocalized(),
			)
		} else if (mediaDetail is MangaDetail) {
			SelectionContainer {
				MediaInfoView(
					title = "Authors",
					info = mediaDetail.authors.joinToString { "${it.node.firstName} ${it.node.lastName}" },
				)
			}
			SelectionContainer {
				MediaInfoView(
					title = "Serialization",
					info = mediaDetail.serialization.joinToString { it.node.name },
				)
			}
			HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
		}
		MediaInfoView(
			title = "Start Date",
			info = mediaDetail.startDate?.formatDate() ?: "N/A",
		)
		MediaInfoView(
			title = "End Date",
			info = mediaDetail.endDate?.formatDate() ?: "N/A",
		)
		if (mediaDetail is AnimeDetail) {
			MediaInfoView(
				title = "Season",
				info = mediaDetail.startSeason?.formatStartSeason() ?: "N/A",
			)
			MediaInfoView(
				title = "Broadcast",
				info = mediaDetail.broadcast?.formatBroadcast() ?: "N/A",
			)
			MediaInfoView(
				title = "Source",
				info = mediaDetail.source.displayName,
			)
		}
		HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
		if (mediaDetail is AnimeDetail) {
			SelectionContainer {
				MediaInfoView(
					title = "Studios",
					info = mediaDetail.studios.joinToString { it.name },
				)
			}
			HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
		}
		mediaDetail.alternativeTitles.synonyms.let { synonyms ->
			SelectionContainer {
				MediaInfoView(
					title = "Synonyms",
					info = synonyms.joinToString { it },
				)
			}
		}
		SelectionContainer {
			MediaInfoView(
				title = "Japanese Title",
				info = mediaDetail.alternativeTitles.ja,
			)
		}
		SelectionContainer {
			MediaInfoView(
				title = "Romaji Title",
				info = mediaDetail.title,
			)
		}
		SelectionContainer {
			MediaInfoView(
				title = "English Title",
				info = mediaDetail.alternativeTitles.en,
			)
		}
	}
}

@Composable
fun RelatedSection(navController: NavController) {
	val mediaDetail = LocalMediaDetail.current

	Column(
		modifier = Modifier.padding(bottom = 16.dp)
	) {
		mediaDetail.relatedAnime?.mapNotNull { it?.node }?.let {
			MediaListView(
				navController = navController,
				title = "Related Anime",
				mediaList = it,
				type = ScreenType.ANIME
			)
		}

		mediaDetail.relatedManga?.mapNotNull { it?.node }?.let {
			MediaListView(
				navController = navController,
				title = "Related Manga",
				mediaList = it,
				type = ScreenType.MANGA
			)
		}
	}
}

@Composable
fun RecommendationSection(
	navController: NavController, type: String
) {
	val mediaDetail = LocalMediaDetail.current

	Column(
		modifier = Modifier.padding(bottom = 16.dp)
	) {
		mediaDetail.recommendations?.map { it.node }?.let {
			MediaListView(
				navController = navController,
				title = "Recommendations",
				mediaList = it,
				type = if (ScreenType.valueOf(type) == ScreenType.ANIME) ScreenType.ANIME else ScreenType.MANGA
			)
		}
	}
}

@Composable
fun MyListFloatingActionButton(detail: MediaDetail, showBottomSheet: MutableState<Boolean>) {
	val editIcon = R.drawable.edit_24px

	val statusMap: Map<String?, Pair<String, Int>> = MyListMediaStatus.entries.associate {
		it.displayName to (it.displayName to editIcon)
	} + (null to ("Add to List" to R.drawable.add_24px))

	val (text, icon) = statusMap[detail.myListStatus?.status?.displayName]
		?: ("Add to List" to R.drawable.add_24px)

	ExtendedFloatingActionButton(
		onClick = {
			showBottomSheet.value = true
		},
		icon = {
			Icon(
				painter = painterResource(id = icon),
				contentDescription = text,
			)
		},
		text = { Text(text = text) },
		modifier = Modifier.padding(16.dp)
	)
}

@Preview
@Composable
fun DetailScreenPreview() {
	val context = LocalContext.current

	KoinApplication(application = {
		androidContext(context)
		modules(
			viewModelModule,
			repositoryModule,
			useCaseModule,
			apiServiceModule
		)
	}) {
		DetailScreen(
			navController = rememberNavController(),
			type = ScreenType.ANIME.name
		)
	}
}
