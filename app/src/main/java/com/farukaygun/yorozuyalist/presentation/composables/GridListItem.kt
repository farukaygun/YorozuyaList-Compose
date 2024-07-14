package com.farukaygun.yorozuyalist.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.model.Data
import com.farukaygun.yorozuyalist.domain.model.ListStatus
import com.farukaygun.yorozuyalist.domain.model.Node
import com.farukaygun.yorozuyalist.domain.model.Ranking
import com.farukaygun.yorozuyalist.domain.model.anime.MainPicture
import com.farukaygun.yorozuyalist.domain.model.anime.StartSeason

@Suppress("UNNECESSARY_SAFE_CALL")
@Composable
fun GridListItem(
	data: Data,
	onItemClick: (Data) -> Unit
) {
	val title = data.node.title?.takeUnless { it.isEmpty() } ?: "N/A"
	val mainPictureUrl = data.node.mainPicture?.medium?.takeUnless { it.isEmpty() }
		?: R.drawable.outline_broken_image_24px

	Column(
		modifier = Modifier
			.padding(8.dp)
			.fillMaxWidth(),
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

		Text(
			text = title,
			textAlign = TextAlign.Start,
			maxLines = 2,
			overflow = TextOverflow.Ellipsis,
			modifier = Modifier.width(100.dp),
			color = MaterialTheme.colorScheme.onSurface,
			style = MaterialTheme.typography.titleMedium
		)

	}
}

@Composable
@Preview
fun GridListItemPreview(
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
	GridListItem(data = data, onItemClick = {})
}