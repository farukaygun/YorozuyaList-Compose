package com.farukaygun.yorozuyalist.presentation.home.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.farukaygun.yorozuyalist.domain.model.Data

@Composable
fun ListItemRow(
	data: Data,
	onItemClick : (Data) -> Unit
) {
	Column(modifier = Modifier
		.padding(8.dp)
		.width(100.dp),
		horizontalAlignment = Alignment.CenterHorizontally) {

		SubcomposeAsyncImage(
			model = ImageRequest.Builder(LocalContext.current)
				.data(data.node.mainPicture.medium)
				.crossfade(true)
				.build(),
			loading = {
				Box(
					modifier = Modifier
						.fillMaxWidth(),
					contentAlignment = Alignment.Center
				) {
					CircularProgressIndicator(
						modifier = Modifier
							.wrapContentHeight()
							.align(Alignment.Center) // Centers the indicator vertically
					)
				}
		    },
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
			color = Color.White,
		)
	}
}