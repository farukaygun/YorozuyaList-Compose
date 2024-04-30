package com.farukaygun.yorozuyalist.presentation.home.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.model.Data
import com.farukaygun.yorozuyalist.domain.model.ListStatus
import com.farukaygun.yorozuyalist.domain.model.Node
import com.farukaygun.yorozuyalist.domain.model.Ranking
import com.farukaygun.yorozuyalist.domain.model.anime.MainPicture
import com.farukaygun.yorozuyalist.domain.model.anime.StartSeason

@Composable
fun ListItemRow(
	data: Data,
	onItemClick : (Data) -> Unit
) {
	Column(modifier = Modifier
		.padding(8.dp)
		.width(100.dp),
		horizontalAlignment = Alignment.CenterHorizontally) {

		AsyncImage(
			model = ImageRequest.Builder(LocalContext.current)
				.data(data.node.mainPicture.medium)
				.crossfade(true)
				.crossfade(500)
				.build(),
			placeholder = painterResource(id = R.drawable.overflow),
			contentDescription = data.node.title,
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.clip(RoundedCornerShape(10.dp))
				.size(100.dp, 150.dp)
		)

		Spacer(modifier = Modifier.height(8.dp))

		Text(
			text = data.node.title,
			textAlign = TextAlign.Center,
			maxLines = 2,
			overflow = TextOverflow.Ellipsis,
			color = MaterialTheme.colorScheme.onSurface,
		)
	}
}

@Composable
@Preview
fun ListItemRowPreview(
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
			score = 10,
			numEpisodesWatched = 12,
			isRewatching = false,
			updatedAt = ""
		),
		ranking = Ranking(
			rank = 1,
		)
	)
	ListItemRow(data = data, onItemClick = {})
}