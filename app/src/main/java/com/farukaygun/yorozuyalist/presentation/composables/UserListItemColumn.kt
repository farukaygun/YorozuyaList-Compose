package com.farukaygun.yorozuyalist.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.model.Data
import com.farukaygun.yorozuyalist.domain.model.ListStatus
import com.farukaygun.yorozuyalist.domain.model.Node
import com.farukaygun.yorozuyalist.domain.model.Ranking
import com.farukaygun.yorozuyalist.domain.model.anime.MainPicture
import com.farukaygun.yorozuyalist.domain.model.anime.StartSeason

// If I delete the safe call, it gives a null pointer exception error when loadMore triggered.
@Suppress("UNNECESSARY_SAFE_CALL")
@Composable
fun UserListItemColumn(
	data: Data,
	onItemClick: (Data) -> Unit
) {
	val title = data.node.title?.takeUnless { it.isEmpty() } ?: "N/A"
	val mediaType = data.node.mediaType?.takeUnless { it.isEmpty() }?.capitalize(Locale.current)
		?: "N/A"
	val numEpisodes =
		data.node.numEpisodes?.toString().takeUnless { it.isNullOrEmpty() } ?: "N/A"
	val mainPictureUrl =
		data.node.mainPicture?.medium?.takeUnless { it.isEmpty() } ?: R.drawable.overflow
	val season =
		data.node.startSeason?.season?.takeUnless { it.isEmpty() }?.capitalize(Locale.current)
			?: "N/A"
	val year =
		data.node.startSeason?.year?.toString().takeUnless { it.isNullOrEmpty() } ?: "N/A"
	val meanScore = data.node.mean?.takeUnless { it.isEmpty() } ?: "N/A"
	val userScore = data.listStatus.score?.takeUnless { it.isEmpty() } ?: "N/A"

	Row(
		modifier = Modifier
			.padding(8.dp)
			.fillMaxWidth(),
	) {
		Surface(
			modifier = Modifier
				.clip(RoundedCornerShape(10.dp))
				.size(100.dp, 150.dp)
				.background(MaterialTheme.colorScheme.surface)
		) {
			SubcomposeAsyncImage(
				model = ImageRequest.Builder(LocalContext.current)
					.data(mainPictureUrl)
					.crossfade(true)
					.crossfade(300)
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
						painter = painterResource(id = R.drawable.outline_broken_image_24px),
						contentDescription = "Error icon",
					)
				},
				contentDescription = title,
				contentScale = ContentScale.Crop,
				modifier = Modifier
					.clip(RoundedCornerShape(10.dp))
					.size(100.dp, 150.dp)
			)

			Box(contentAlignment = Alignment.BottomStart) {
				Row(
					modifier = Modifier
						.background(MaterialTheme.colorScheme.surfaceVariant)
						.padding(horizontal = 4.dp, vertical = 4.dp),
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = userScore,
						modifier = Modifier.padding(horizontal = 4.dp),
						style = MaterialTheme.typography.bodyMedium,
						textAlign = TextAlign.Center,
						color = MaterialTheme.colorScheme.onSurface
					)
					Icon(
						painter = painterResource(id = R.drawable.outline_filled_grade_16px),
						contentDescription = "Grade",
						tint = MaterialTheme.colorScheme.onBackground
					)
				}
			}
		}

		Column(
			modifier = Modifier.padding(
				horizontal = 16.dp,
				vertical = 4.dp
			)
		) {
			Text(
				text = title,
				textAlign = TextAlign.Start,
				maxLines = 2,
				overflow = TextOverflow.Ellipsis,
				color = MaterialTheme.colorScheme.onSurface,
				style = MaterialTheme.typography.titleMedium
			)

			Spacer(modifier = Modifier.height(8.dp))

			Row(
				verticalAlignment = Alignment.CenterVertically
			) {
				Icon(
					painter = painterResource(id = R.drawable.outline_tv_24px),
					contentDescription = "Media type icon",
					modifier = Modifier.padding(end = 4.dp)
				)

				Text(
					text = "$mediaType ($numEpisodes episodes)",
					textAlign = TextAlign.Center,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					color = MaterialTheme.colorScheme.onSurface,
					style = MaterialTheme.typography.bodyMedium
				)
			}

			Spacer(modifier = Modifier.height(8.dp))

			Row(
				verticalAlignment = Alignment.CenterVertically
			) {
				Icon(
					painter = painterResource(id = R.drawable.outline_calendar_month_24px),
					contentDescription = "Season icon",
					modifier = Modifier.padding(end = 4.dp)
				)

				Text(
					text = "$season $year",
					textAlign = TextAlign.Center,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					color = MaterialTheme.colorScheme.onSurface,
					style = MaterialTheme.typography.bodyMedium
				)
			}

			Spacer(modifier = Modifier.height(8.dp))

			Row(
				verticalAlignment = Alignment.CenterVertically
			) {
				Icon(
					painter = painterResource(id = R.drawable.outline_filled_grade_24px),
					contentDescription = "Mean score icon",
					modifier = Modifier.padding(end = 4.dp)
				)

				Text(
					text = meanScore,
					textAlign = TextAlign.Center,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					color = MaterialTheme.colorScheme.onSurface,
					style = MaterialTheme.typography.bodyMedium
				)
			}
		}
	}
}


@Composable
@Preview
fun UserListItemColumnPreview(
) {
	val data = Data(
		node = Node(
			id = 52991,
			title = "Sousou no Frieren",
			mainPicture = MainPicture(
				medium = "https:\\/\\/cdn.myanimelist.net\\/images\\/anime\\/1015\\/138006.jpg",
				large = "https:\\/\\/cdn.myanimelist.net\\/images\\/anime\\/1015\\/138006l.jpg"
			),
			status = "finished_airing",
			mean = "9.38",
			mediaType = "tv",
			startSeason = StartSeason(
				year = 2023,
				season = "Fall"
			),
			numListUsers = 701406

		),
		listStatus = ListStatus(
			status = "finished_airing",
			score = "10",
			numEpisodesWatched = 12,
			isRewatching = false,
			updatedAt = ""
		),
		ranking = Ranking(
			rank = 1,
		)
	)
	UserListItemColumn(data = data, onItemClick = {})
}