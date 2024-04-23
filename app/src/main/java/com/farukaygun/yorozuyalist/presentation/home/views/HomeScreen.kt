package com.farukaygun.yorozuyalist.presentation.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.farukaygun.yorozuyalist.domain.model.Data
import com.farukaygun.yorozuyalist.presentation.Screen
import com.farukaygun.yorozuyalist.presentation.home.HomeState
import com.farukaygun.yorozuyalist.presentation.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
	navController: NavController,
	viewModel: HomeViewModel = koinViewModel()
) {
	val state = viewModel.state.value

	LaunchedEffect(Unit) {
		if (viewModel.isLoggedIn()) {
			viewModel.getSeasonalAnime(2023, "fall", 100)
		} else {
			navController.navigate(Screen.LoginScreen.route)
		}
	}

	HomeScreenSection(
		state = state,
		title = "Seasonal Anime"
	)
}

// Common HomeScreen horizontal list.
@Composable
fun HorizontalList(animeList : List<Data>) {
	Surface(
		modifier = Modifier
			.fillMaxSize()
	) {
		Box(
			modifier = Modifier
				.fillMaxSize()
				.background(Color.Black),
			contentAlignment = Alignment.Center
		) {
			LazyRow(modifier = Modifier
				.fillMaxSize()
				.padding(8.dp)) {
				items(animeList) {anime ->
					ListItemRow(data = anime, onItemClick = {
						// navController.navigate(Screen.AnimeDetailScreen.route+"/${anime.node.id}")
					})
				}
			}
		}
	}
}

@Composable
fun HomeScreenSection(state: HomeState, title: String) {
	Surface {
		Box(
			modifier = Modifier
				.fillMaxSize()
				.background(Color.Black),
			contentAlignment = Alignment.Center,

		) {
			Column {
				SectionTitle(title)

				if (state.isLoading) {
					Box(
						modifier = Modifier
							.fillMaxWidth()
							.defaultMinSize(minHeight = 170.dp),
						contentAlignment = Alignment.Center
					) {
						CircularProgressIndicator(
							modifier = Modifier
								.wrapContentHeight()
								.align(Alignment.Center) // Centers the indicator vertically
						)
					}
				}

				HorizontalList(state.animeSeasonalList)
			}
		}
	}
}

@Composable
fun SectionTitle(title: String) {
	Text(
		text = title,
		modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 0.dp),
		color = Color.White,
		fontSize = 24.sp,
	)
}