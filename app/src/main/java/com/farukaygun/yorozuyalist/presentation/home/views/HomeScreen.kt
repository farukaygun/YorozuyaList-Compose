package com.farukaygun.yorozuyalist.presentation.home.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.farukaygun.yorozuyalist.data.di.apiServiceModule
import com.farukaygun.yorozuyalist.data.di.repositoryModule
import com.farukaygun.yorozuyalist.data.di.useCaseModule
import com.farukaygun.yorozuyalist.data.di.viewModelModule
import com.farukaygun.yorozuyalist.domain.model.Data
import com.farukaygun.yorozuyalist.presentation.Screen
import com.farukaygun.yorozuyalist.presentation.composables.ListItemRow
import com.farukaygun.yorozuyalist.presentation.home.HomeEvent
import com.farukaygun.yorozuyalist.presentation.home.HomeState
import com.farukaygun.yorozuyalist.presentation.home.HomeViewModel
import com.farukaygun.yorozuyalist.util.GridListType
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Composable
fun HomeScreen(
	navController: NavController,
	viewModel: HomeViewModel = koinViewModel()
) {
	val state = viewModel.state.value

	LaunchedEffect(Unit) {
		if (viewModel.isLoggedIn())
			viewModel.onEvent(HomeEvent.InitRequestChain)
		else navController.navigate(route = Screen.LoginScreen.route)
	}

	Column(
		modifier = Modifier
			.verticalScroll(rememberScrollState())
	) {
		HomeScreenSection(
			data = state.animeTodayList,
			state = state,
			title = "Today"
		)
		HomeScreenSection(
			data = state.animeSeasonalList,
			state = state,
			title = "Seasonal Anime",
			onClick = { navController.navigate(Screen.GridListScreen.route + "/${GridListType.SEASONAL_ANIME_LIST.name}") }
		)
		HomeScreenSection(
			data = state.animeSuggestionList,
			state = state,
			title = "Suggested Anime",
			onClick = { navController.navigate(Screen.GridListScreen.route + "/${GridListType.SUGGESTED_ANIME_LIST.name}") }
		)
	}
}

@Composable
fun HomeScreenSection(
	data: List<Data>,
	state: HomeState,
	title: String,
	onClick: () -> Unit = {}
) {
	Surface {
		Box(
			modifier = Modifier
				.padding(horizontal = 16.dp, vertical = 8.dp),
			contentAlignment = Alignment.Center,

			) {
			Column {
				SectionTitle(title = title, onClick = onClick)

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
								.align(Alignment.Center)
						)
					}
				}
				HorizontalList(data)
			}
		}
	}
}

@Composable
fun SectionTitle(title: String, onClick: () -> Unit = {}) {
	Box(
		modifier = Modifier
			.padding(8.dp)
			.fillMaxWidth()
			.clickable { onClick() }
	) {
		Text(
			text = title,
			color = MaterialTheme.colorScheme.onSurface,
			textAlign = TextAlign.Center,
			style = MaterialTheme.typography.titleLarge,
			modifier = Modifier.align(Alignment.CenterStart),
		)
		Text(
			text = "MORE",
			color = MaterialTheme.colorScheme.onSurface,
			textAlign = TextAlign.Center,
			style = MaterialTheme.typography.titleMedium,
			modifier = Modifier.align(Alignment.BottomEnd),
		)
	}
}

@Composable
fun HorizontalList(animeList: List<Data>) {
	Surface(
		modifier = Modifier
			.fillMaxWidth()
	) {
		Box(
			modifier = Modifier
				.fillMaxWidth(),
			contentAlignment = Alignment.Center
		) {
			LazyRow(
				modifier = Modifier
					.fillMaxWidth()
					.padding(8.dp)
			) {
				items(animeList) { anime ->
					ListItemRow(data = anime, onItemClick = {
						// navController.navigate(Screen.AnimeDetailScreen.route+"/${anime.node.id}")
					})
				}
			}
		}
	}
}

@Composable
@Preview
fun HomeScreenPreview() {
	val context = LocalContext.current

	KoinApplication(application = {
		androidContext(context)
		modules(
			viewModelModule,
			repositoryModule,
			useCaseModule,
			apiServiceModule
		)
	}) {
		HomeScreen(
			navController = rememberNavController()
		)
	}
}