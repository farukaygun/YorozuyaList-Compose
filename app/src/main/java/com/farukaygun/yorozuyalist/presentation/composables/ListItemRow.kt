package com.farukaygun.yorozuyalist.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.models.MainPicture
import com.farukaygun.yorozuyalist.domain.models.Node
import com.farukaygun.yorozuyalist.domain.models.anime.Broadcast
import com.farukaygun.yorozuyalist.domain.models.anime.StartSeason
import com.farukaygun.yorozuyalist.domain.models.enums.MediaType
import com.farukaygun.yorozuyalist.domain.models.enums.Season
import com.farukaygun.yorozuyalist.presentation.composables.shimmer_effect.ShimmerEffect

private const val IMAGE_WIDTH = 100
private const val IMAGE_HEIGHT = 150

@Composable
fun ListItemRow(
	node: Node,
	onItemClick: () -> Unit
) {
	Column(
		modifier = Modifier
			.width(IMAGE_WIDTH.dp)
			.clickable { onItemClick() },
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(8.dp)
	) {
		SubcomposeAsyncImage(
			model = ImageRequest.Builder(LocalContext.current)
				.data(node.mainPicture.medium)
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
			contentDescription = node.title,
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.clip(RoundedCornerShape(10.dp))
				.size(IMAGE_WIDTH.dp, IMAGE_HEIGHT.dp)
		)

		Text(
			text = node.title,
			textAlign = TextAlign.Start,
			maxLines = 2,
			overflow = TextOverflow.Ellipsis,
			modifier = Modifier.width(IMAGE_WIDTH.dp),
			color = MaterialTheme.colorScheme.onBackground,
			style = MaterialTheme.typography.bodyMedium
		)
	}
}

@Composable
fun ShimmerEffectItemRow() {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
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
fun ListItemRowPreview(
) {
	val data = Node(
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
	)
	ListItemRow(node = data, onItemClick = {})
}