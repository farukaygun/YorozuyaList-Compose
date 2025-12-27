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
import androidx.compose.ui.text.font.FontWeight
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
fun UserListItemColumn(
	data: Data,
	onItemClick: () -> Unit
) {
	val node = data.node
	val title = node.title
	val mediaType = node.mediaType?.displayName
	val numEpisodes = if (node.numEpisodes > 0) "(${node.numEpisodes} episodes)" else ""
	val mainPictureUrl = node.mainPicture?.medium
	val season = node.startSeason?.season?.displayName ?: "N/A"
	val year = node.startSeason?.year?.toString()
	val meanScore = node.mean ?: "N/A"
	val userScore = data.myListStatus?.score
	
	Card(
		onClick = onItemClick,
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surface,
		),
		elevation = CardDefaults.cardElevation(
			defaultElevation = 2.dp
		),
		modifier = Modifier.fillMaxWidth()
			.padding(vertical = 8.dp)
	) {
		Row(
			verticalAlignment = Alignment.Top,
			horizontalArrangement = Arrangement.spacedBy(16.dp)
		) {
			MediaImageWithScore(
				imageUrl = mainPictureUrl,
				contentDescription = title,
				userScore = userScore
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
private fun MediaImageWithScore(
	imageUrl: String?,
	contentDescription: String,
	userScore: Int?,
	modifier: Modifier = Modifier
) {
	Box(modifier = modifier) {
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
				ErrorImagePlaceHolder()
			},
			contentDescription = contentDescription,
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.clip(RoundedCornerShape(10.dp))
				.size(IMAGE_WIDTH.dp, IMAGE_HEIGHT.dp)
		)
		
		if (userScore != null) {
			if (userScore > 0) {
				UserScoreBadge(
					score = userScore,
					modifier = Modifier.align(Alignment.BottomStart)
				)
			}
		}
	}
}

@Composable
private fun ErrorImagePlaceHolder(
	modifier: Modifier = Modifier
) {
	Box(
		modifier = modifier
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
}

@Composable
private fun UserScoreBadge(
	score: Int,
	modifier: Modifier = Modifier
) {
	Row(
		modifier = modifier
			.background(
				MaterialTheme.colorScheme.primary,
				RoundedCornerShape(bottomStart = 8.dp, topEnd = 8.dp)
			)
			.padding(horizontal = 6.dp, vertical = 4.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(2.dp)
	) {
		Text(
			text = score.toString(),
			style = MaterialTheme.typography.labelMedium,
			color = MaterialTheme.colorScheme.onPrimary,
			fontWeight = FontWeight.Bold
		)
		Icon(
			painter = painterResource(R.drawable.grade_16px),
			contentDescription = null,
			tint = MaterialTheme.colorScheme.onPrimary,
			modifier = Modifier.size(12.dp)
		)
	}
}

@Composable
private fun MediaInfo(
	title: String,
	mediaType: String?,
	numEpisodes: String?,
	season: String?,
	year: String?,
	meanScore: String,
) {
	Column(
		verticalArrangement = Arrangement.spacedBy(8.dp),
		modifier = Modifier
			.fillMaxWidth()
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
fun UserListItemColumnPreview(
) {
	UserListItemColumn(data = Constants.PREVIEW_SAMPLE_DATA, onItemClick = {})
}