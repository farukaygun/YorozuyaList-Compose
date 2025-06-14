package com.farukaygun.yorozuyalist.presentation.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.presentation.composables.shimmer_effect.ShimmerEffect
import com.farukaygun.yorozuyalist.util.Constants

private const val IMAGE_WIDTH = 120
private const val IMAGE_HEIGHT = 150

@Composable
fun ListItemColumn(
	data: Data,
	onItemClick: () -> Unit
) {
	val node = data.node
	val title = node.title
	val mediaType = node.mediaType?.displayName
	val numEpisodes = if (node.numEpisodes > 0) "(${node.numEpisodes} episodes)" else ""
	val mainPictureUrl = node.mainPicture.medium
	val season = node.startSeason?.season?.displayName ?: "N/A"
	val year = node.startSeason?.year?.toString()
	val meanScore = node.mean?.takeUnless { it.isEmpty() } ?: "N/A"

	Card(
		onClick = onItemClick,
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surface,
		),
		elevation = CardDefaults.cardElevation(
			defaultElevation = 2.dp
		),
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 8.dp)
	) {
		Row(
			verticalAlignment = Alignment.Top,
			horizontalArrangement = Arrangement.spacedBy(16.dp)
		) {
			MediaImage(
				imageUrl = mainPictureUrl,
				contentDescription = title
			)
			
			MediaInfo(
				title = title,
				mediaType = mediaType,
				numEpisodes = numEpisodes,
				season = season,
				year = year,
				meanScore = meanScore
			)
		}
	}
}

@Composable
private fun MediaImage(
	imageUrl: String?,
	contentDescription: String,
	modifier: Modifier = Modifier
) {
	SubcomposeAsyncImage(
		model = ImageRequest.Builder(LocalContext.current)
			.data(imageUrl)
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
			Box(
				modifier = Modifier
					.size(IMAGE_WIDTH.dp, IMAGE_HEIGHT.dp)
					.background(
						MaterialTheme.colorScheme.surface,
						RoundedCornerShape(10.dp)
					),
				contentAlignment = Alignment.Center
			) {
				Icon(
					painter = painterResource(R.drawable.broken_image_24px),
					contentDescription = "Error loading image",
					tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
				)
			}
		},
		contentDescription = contentDescription,
		contentScale = ContentScale.Crop,
		modifier = modifier
			.clip(RoundedCornerShape(10.dp))
			.size(IMAGE_WIDTH.dp, IMAGE_HEIGHT.dp)
	)
}

@Composable
private fun MediaInfo(
	title: String,
	mediaType: String?,
	numEpisodes: String?,
	season: String?,
	year: String?,
	meanScore: String,
	modifier: Modifier = Modifier
) {
	Column(
		verticalArrangement = Arrangement.spacedBy(8.dp),
		modifier =
			modifier.fillMaxWidth()
			.padding(8.dp)
	) {
		Text(
			text = title,
			minLines = 2,
			maxLines = 2,
			overflow = TextOverflow.Ellipsis,
			color = MaterialTheme.colorScheme.onSurfaceVariant,
			style = MaterialTheme.typography.titleMedium
		)
		
		InfoRow(
			iconRes = R.drawable.tv_24px,
			text = buildMediaTypeText(mediaType, numEpisodes),
			contentDescription = "Media type"
		)
		
		InfoRow(
			iconRes = R.drawable.calendar_month_24px,
			text = buildSeasonText(season, year),
			contentDescription = "Season and year"
		)
		
		InfoRow(
			iconRes = R.drawable.grade_24px,
			text = meanScore,
			contentDescription = "Rating"
		)
	}
}

@Composable
private fun InfoRow(
	@DrawableRes iconRes: Int,
	text: String,
	contentDescription: String,
	modifier: Modifier = Modifier
) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(4.dp),
		modifier = modifier
	) {
		Icon(
			painter = painterResource(iconRes),
			contentDescription = contentDescription,
			modifier = Modifier.size(18.dp),
			tint = MaterialTheme.colorScheme.onSurfaceVariant
		)
		
		Text(
			text = text,
			maxLines = 1,
			overflow = TextOverflow.Ellipsis,
			color = MaterialTheme.colorScheme.onSurfaceVariant,
			style = MaterialTheme.typography.bodySmall
		)
	}
}

@Composable
fun ShimmerEffectItemColumn() {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 8.dp),
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

private fun buildMediaTypeText(mediaType: String?, numEpisodes: String?): String {
	return when {
		!mediaType.isNullOrEmpty() -> "$mediaType $numEpisodes"
		else -> "Unknown"
	}
}

private fun buildSeasonText(season: String?, year: String?): String {
	return when (year) {
		null -> "N/A"
		else -> "$season $year"
	}
}

@Composable
@Preview
fun ListItemColumnPreview(
) {
	ListItemColumn(data = Constants.PREVIEW_SAMPLE_DATA, onItemClick = {})
}