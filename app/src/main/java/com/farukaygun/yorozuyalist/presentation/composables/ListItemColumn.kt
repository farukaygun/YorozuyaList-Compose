package com.farukaygun.yorozuyalist.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.common.extensions.isNotNull
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
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
fun ListItemColumn(
	data: Data,
	onItemClick: () -> Unit
) {
	val title = data.node.title
	val mediaType = data.node.mediaType?.format()
	val numEpisodes = if (data.node.numEpisodes > 0) "(${data.node.numEpisodes} episodes)" else ""
	val mainPictureUrl = data.node.mainPicture.medium
	val season = data.node.startSeason?.season?.format() ?: "Unknown"
	val year = data.node.startSeason?.year?.toString()
	val meanScore = data.node.mean?.takeUnless { it.isEmpty() } ?: "N/A"

	Row(
		modifier = Modifier
			.fillMaxWidth()
			.clickable { onItemClick() },
		verticalAlignment = Alignment.Top,
		horizontalArrangement = Arrangement.spacedBy(16.dp)
	) {
		SubcomposeAsyncImage(
			model = ImageRequest.Builder(LocalContext.current)
				.data(mainPictureUrl)
				.crossfade(true)
				.crossfade(300)
				.build(),
//			loading = {
//				Column(
//					verticalArrangement = Arrangement.Center,
//					horizontalAlignment = Alignment.CenterHorizontally
//				) {
//					CircularProgressIndicator()
//				}
//			},
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
fun ShimmerEffectItemColumn() {
	Row(
		modifier = Modifier
			.fillMaxWidth(),
		horizontalArrangement = Arrangement.spacedBy(16.dp),
		verticalAlignment = Alignment.Top
	) {
		ShimmerEffect(
			modifier = Modifier
				.clip(RoundedCornerShape(10.dp))
				.size(IMAGE_WIDTH.dp, IMAGE_HEIGHT.dp)
		)

		Column(
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			ShimmerEffect(
				modifier = Modifier
					.clip(RoundedCornerShape(10.dp))
					.size(300.dp, 40.dp)
			)

			Row(
				verticalAlignment = Alignment.CenterVertically
			) {
				ShimmerEffect(
					modifier = Modifier
						.clip(RoundedCornerShape(10.dp))
						.size(IMAGE_WIDTH.dp, 24.dp)
				)
			}

			Row(
				verticalAlignment = Alignment.CenterVertically
			) {
				ShimmerEffect(
					modifier = Modifier
						.clip(RoundedCornerShape(10.dp))
						.size(IMAGE_WIDTH.dp, 24.dp)
				)
			}

			Row(
				verticalAlignment = Alignment.CenterVertically
			) {
				ShimmerEffect(
					modifier = Modifier
						.clip(RoundedCornerShape(10.dp))
						.size(IMAGE_WIDTH.dp, 24.dp)
				)
			}
		}
	}
}

@Composable
@Preview
fun ListItemColumnPreview(
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
	ListItemColumn(data = data, onItemClick = {})
}