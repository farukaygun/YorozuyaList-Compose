package com.farukaygun.yorozuyalist.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.text.style.TextAlign
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
fun GridListItemWithRank(
	data: Data,
	onItemClick: () -> Unit
) {
	Card(
		onClick = onItemClick,
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surface,
		),
		elevation = CardDefaults.cardElevation(
			defaultElevation = 2.dp
		),
		modifier = Modifier
			.width(IMAGE_WIDTH.dp)
			.padding(horizontal = 4.dp)
	) {
		MediaImageWithScore(
			imageUrl = data.node.mainPicture.medium,
			contentDescription = data.node.title,
			rank = data.ranking?.rank
		)
		
		MediaTitle(
			title = data.node.title,
			modifier = Modifier
				.fillMaxWidth()
				.padding(4.dp)
		)
	}
}

@Composable
private fun MediaImageWithScore(
	imageUrl: String?,
	contentDescription: String,
	rank: Int?,
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
				ErrorImagePlaceholder()
			},
			contentDescription = contentDescription,
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.clip(RoundedCornerShape(10.dp))
				.size(IMAGE_WIDTH.dp, IMAGE_HEIGHT.dp)
		)

		if (rank != null && rank > 0) {
			RankBadge(
				score = rank,
				modifier = Modifier.align(Alignment.BottomStart)
			)
		}
	}
}

@Composable
private fun RankBadge(
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
			text = "#$score",
			style = MaterialTheme.typography.labelMedium,
			color = MaterialTheme.colorScheme.onPrimary,
			fontWeight = FontWeight.Bold
		)
	}
}

@Composable
private fun ErrorImagePlaceholder(
	modifier: Modifier = Modifier
) {
	Box(
		modifier = modifier
			.size(IMAGE_WIDTH.dp, IMAGE_HEIGHT.dp)
			.background(
				MaterialTheme.colorScheme.surfaceVariant,
				RoundedCornerShape(10.dp)
			),
		contentAlignment = Alignment.Center
	) {
		Icon(
			painter = painterResource(R.drawable.broken_image_24px),
			contentDescription = "Error loading image",
			tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
			modifier = modifier.size(32.dp)
		)
	}
}

@Composable
private fun MediaTitle(
	title: String,
	modifier: Modifier = Modifier
) {
	Text(
		text = title,
		textAlign = TextAlign.Center,
		minLines = 2,
		maxLines = 2,
		overflow = TextOverflow.Ellipsis,
		color = MaterialTheme.colorScheme.onSurface,
		style = MaterialTheme.typography.bodyMedium,
		lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
		modifier = modifier
	)
}

@Composable
fun ShimmerEffectGridListItemWithRank() {
	Column(
		modifier = Modifier
			.wrapContentWidth(),
		verticalArrangement = Arrangement.spacedBy(8.dp)
	) {
		ShimmerEffect(
			modifier = Modifier
				.clip(RoundedCornerShape(10.dp))
				.size(IMAGE_WIDTH.dp, IMAGE_HEIGHT.dp)
		)

		ShimmerEffect(
			modifier = Modifier
				.clip(RoundedCornerShape(10.dp))
				.size(IMAGE_WIDTH.dp, 40.dp)
		)
	}
}

@Composable
@Preview
fun GridListItemWithRankPreview(
) {
	GridListItemWithRank(data = Constants.PREVIEW_SAMPLE_DATA, onItemClick = {})
}