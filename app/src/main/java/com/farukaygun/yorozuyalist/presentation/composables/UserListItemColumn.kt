package com.farukaygun.yorozuyalist.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.common.extensions.isNotNull
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.MainPicture
import com.farukaygun.yorozuyalist.domain.models.MyListStatus
import com.farukaygun.yorozuyalist.domain.models.Node
import com.farukaygun.yorozuyalist.domain.models.Ranking
import com.farukaygun.yorozuyalist.domain.models.anime.Broadcast
import com.farukaygun.yorozuyalist.domain.models.anime.StartSeason
import com.farukaygun.yorozuyalist.domain.models.enums.MediaType
import com.farukaygun.yorozuyalist.domain.models.enums.MyListMediaStatus
import com.farukaygun.yorozuyalist.domain.models.enums.Season
import com.farukaygun.yorozuyalist.presentation.composables.shimmer_effect.ShimmerEffect

private const val IMAGE_WIDTH = 100
private const val IMAGE_HEIGHT = 150

@Composable
fun UserListItemColumn(
	data: Data,
	onItemClick: () -> Unit
) {
	val title = data.node.title
	val mediaType = data.node.mediaType?.displayName
	val numEpisodes = if (data.node.numEpisodes > 0) "(${data.node.numEpisodes} episodes)" else ""
	val mainPictureUrl = data.node.mainPicture.medium
	val season = data.node.startSeason?.season?.displayName ?: "Unknown"
	val year = data.node.startSeason?.year?.toString()
	val meanScore = data.node.mean ?: "Unknown"
	val userScore = data.myListStatus?.score

	Row(
		modifier = Modifier
			.fillMaxWidth()
			.clickable { onItemClick() },
		horizontalArrangement = Arrangement.spacedBy(16.dp)
	) {
		Surface(
			modifier = Modifier
				.clip(RoundedCornerShape(10.dp))
				.size(IMAGE_WIDTH.dp, IMAGE_HEIGHT.dp)
				.background(MaterialTheme.colorScheme.surface)
		) {
			SubcomposeAsyncImage(
				model = ImageRequest.Builder(LocalContext.current)
					.data(mainPictureUrl)
					.crossfade(true)
					.crossfade(350)
					.build(),
				loading = {
					ShimmerEffect(
						modifier = Modifier
							.clip(RoundedCornerShape(10.dp))
							.size(IMAGE_WIDTH.dp, IMAGE_HEIGHT.dp)
					)
				},
				error = {
					Icon(
						painter = painterResource(id = R.drawable.broken_image_24px),
						contentDescription = "Error icon",
					)
				},
				contentDescription = title,
				contentScale = ContentScale.Crop,
				modifier = Modifier
					.clip(RoundedCornerShape(10.dp))
					.size(IMAGE_WIDTH.dp, IMAGE_HEIGHT.dp)
			)

			Box(contentAlignment = Alignment.BottomStart) {
				Row(
					modifier = Modifier
						.background(MaterialTheme.colorScheme.primary)
						.padding(4.dp),
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(4.dp)
				) {
					Text(
						text = userScore.toString(),
						style = MaterialTheme.typography.bodyMedium,
						textAlign = TextAlign.Center,
						color = MaterialTheme.colorScheme.onPrimary,
						fontWeight = FontWeight.Bold
					)
					Icon(
						painter = painterResource(id = R.drawable.grade_16px),
						contentDescription = "Grade",
						tint = MaterialTheme.colorScheme.onPrimary
					)
				}
			}
		}

		Column(
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			Text(
				text = title,
				textAlign = TextAlign.Start,
				maxLines = 2,
				overflow = TextOverflow.Ellipsis,
				color = MaterialTheme.colorScheme.onSurface,
				style = MaterialTheme.typography.titleMedium
			)

			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(4.dp)
			) {
				Icon(
					painter = painterResource(id = R.drawable.tv_24px),
					contentDescription = "Media type icon",
				)

				Text(
					text = if (mediaType.isNotNull()) "$mediaType $numEpisodes" else "Unknown",
					textAlign = TextAlign.Center,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					color = MaterialTheme.colorScheme.onSurface,
					style = MaterialTheme.typography.bodyMedium
				)
			}

			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(4.dp)
			) {
				Icon(
					painter = painterResource(id = R.drawable.calendar_month_24px),
					contentDescription = "Season icon",
				)

				Text(
					text = if (year != null) "$season $year" else "N/A",
					textAlign = TextAlign.Center,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					color = MaterialTheme.colorScheme.onSurface,
					style = MaterialTheme.typography.bodyMedium
				)
			}

			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(4.dp)
			) {
				Icon(
					painter = painterResource(id = R.drawable.grade_24px),
					contentDescription = "Mean score icon",
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
			mediaType = MediaType.TV,
			startSeason = StartSeason(
				year = 2023,
				season = Season.FALL
			),
			numListUsers = 701406,
			numEpisodes = 12,
			broadcast = Broadcast(
				dayOfTheWeek = "Saturday",
				startTime = "00:00"
			),
		),
		myListStatus = MyListStatus(
			status = MyListMediaStatus.WATCHING,
			score = 10,
			numEpisodesWatched = 12,
			isRewatching = false,
			updatedAt = "",
			startDate = "",
			finishDate = "",
			numTimesRewatched = 0,
			rewatchValue = 0,
			tags = emptyList(),
			priority = 0,
			comments = ""
		),
		ranking = Ranking(
			rank = 1,
		),
		rankingType = "Score"
	)
	UserListItemColumn(data = data, onItemClick = {})
}