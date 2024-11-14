package com.farukaygun.yorozuyalist.presentation.detail.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.farukaygun.yorozuyalist.domain.models.Node
import com.farukaygun.yorozuyalist.presentation.Screen
import com.farukaygun.yorozuyalist.presentation.composables.ListItemRow
import com.farukaygun.yorozuyalist.util.ScreenType

@Composable
fun MediaListView(
	navController: NavController,
	title: String,
	mediaList: List<Node>,
	type: ScreenType
) {
	Column {
		mediaList.takeIf { it.isNotEmpty() }?.let { mediaList ->
			Text(
				text = title,
				style = MaterialTheme.typography.titleMedium,
				color = MaterialTheme.colorScheme.onBackground,
				modifier = Modifier.padding(vertical = 8.dp)
			)
			LazyRow(
				modifier = Modifier
					.fillMaxWidth(),
				horizontalArrangement = Arrangement.spacedBy(16.dp)
			) {
				items(mediaList) { item ->
					ListItemRow(
						node = item,
						onItemClick = { navController.navigate(Screen.DetailScreen.route + "/${type}/${item.id}") }
					)
				}
			}

		}
	}
}