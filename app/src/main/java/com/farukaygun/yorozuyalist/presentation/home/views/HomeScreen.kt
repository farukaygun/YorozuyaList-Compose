package com.farukaygun.yorozuyalist.presentation.home.views

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.data.di.apiServiceModule
import com.farukaygun.yorozuyalist.data.di.repositoryModule
import com.farukaygun.yorozuyalist.data.di.useCaseModule
import com.farukaygun.yorozuyalist.data.di.viewModelModule
import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.presentation.Screen
import com.farukaygun.yorozuyalist.presentation.composables.ListItemRow
import com.farukaygun.yorozuyalist.presentation.composables.shimmer_effect.ShimmerEffectHorizontalList
import com.farukaygun.yorozuyalist.presentation.home.HomeEvent
import com.farukaygun.yorozuyalist.presentation.home.HomeViewModel
import com.farukaygun.yorozuyalist.util.Calendar.Companion.season
import com.farukaygun.yorozuyalist.util.Calendar.Companion.year
import com.farukaygun.yorozuyalist.util.enums.GridListType
import com.farukaygun.yorozuyalist.util.enums.ScreenType
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
	navController: NavController,
	viewModel: HomeViewModel = koinViewModel()
) {
	val state = viewModel.state.value

	PullToRefreshBox(
		isRefreshing = state.isLoading,
		onRefresh = { viewModel.onEvent(event = HomeEvent.InitRequestChain) },
		state = rememberPullToRefreshState(),
		modifier = Modifier.padding(horizontal = 16.dp)
	) {
		Column(
			modifier = Modifier
				.verticalScroll(rememberScrollState()),
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			HomeLargeActionButtons(navController = navController)

			HomeScreenSection(
				navController = navController,
				data = state.animeTodayList,
				isLoading = state.isLoading,
				title = "Today",
				icon = R.drawable.schedule_24px,
				isMoreVisible = false
			)
			HomeScreenSection(
				navController = navController,
				data = state.animeSeasonalList,
				isLoading = state.isLoading,
				title = "${season.displayName} $year Anime",
				icon = season.icon,
				onClick = { navController.navigate(Screen.GridListScreen.route + "/${GridListType.SEASONAL_ANIME_LIST.name}") }
			)
			HomeScreenSection(
				navController = navController,
				data = state.animeSuggestionList,
				isLoading = state.isLoading,
				title = "Suggested Anime",
				icon = R.drawable.for_you_24px,
				onClick = { navController.navigate(Screen.GridListScreen.route + "/${GridListType.SUGGESTED_ANIME_LIST.name}") }
			)
		}

		if (state.error.isNotEmpty()) {
			Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_SHORT).show()
		}
	}
}

@Composable
fun HomeLargeActionButtons(navController: NavController) {
	Row(
		modifier = Modifier
			.fillMaxWidth(),
		horizontalArrangement = Arrangement.spacedBy(8.dp)
	) {
		LargeActionButtonWithIcon(
			text = "Anime Ranking",
			icon = R.drawable.movie_24px,
			onClick = { navController.navigate(Screen.GridListScreen.route + "/${GridListType.RANKING_ANIME_LIST.name}") },
			modifier = Modifier.weight(1f)
		)

		LargeActionButtonWithIcon(
			text = "Manga Ranking",
			icon = R.drawable.book_24px,
			onClick = { navController.navigate(Screen.GridListScreen.route + "/${GridListType.RANKING_MANGA_LIST.name}") },
			modifier = Modifier.weight(1f)
		)
	}
}

@Composable
fun HomeScreenSection(
	navController: NavController,
	data: List<Data>,
	isLoading: Boolean,
	title: String,
	icon: Int,
	onClick: () -> Unit = {},
	isMoreVisible: Boolean = true
) {
	Box(
		contentAlignment = Alignment.Center
	) {
		Column {
			SectionTitle(
				title = title,
				onClick = onClick,
				icon = icon,
				isMoreVisible = isMoreVisible
			)

			if (!isLoading && data.isNotEmpty()) {
				HorizontalList(
					navController = navController,
					animeList = data
				)
			} else {
				ShimmerEffectHorizontalList()
			}
		}
	}
}

@Composable
fun SectionTitle(
	title: String,
	icon: Int,
	onClick: () -> Unit = {},
	isMoreVisible: Boolean = true
) {
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.clickable(
				interactionSource = null,
				indication = null,
				onClick = { if (isMoreVisible) onClick() }
			)
	) {
		Row(
			modifier = Modifier.align(Alignment.CenterStart),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.spacedBy(4.dp)
		) {
			Icon(
				painter = painterResource(id = icon),
				contentDescription = "Today title icon",
			)

			Text(
				text = title,
				color = MaterialTheme.colorScheme.onBackground,
				textAlign = TextAlign.Center,
				style = MaterialTheme.typography.titleMedium,
				fontWeight = FontWeight.Bold
			)
		}

		if (isMoreVisible) {
			Row(
				modifier = Modifier.align(Alignment.BottomEnd),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(4.dp)
			) {
				Text(
					text = "MORE",
					color = MaterialTheme.colorScheme.onBackground,
					textAlign = TextAlign.Center,
					style = MaterialTheme.typography.titleSmall,
					fontWeight = FontWeight.Bold
				)

				Icon(
					painter = painterResource(id = R.drawable.arrow_forward_16px),
					contentDescription = "More icon",
					tint = MaterialTheme.colorScheme.onBackground
				)
			}
		}
	}
}

@Composable
fun HorizontalList(
	navController: NavController,
	animeList: List<Data>
) {
	Box(
		modifier = Modifier
			.fillMaxWidth(),
		contentAlignment = Alignment.Center
	) {
		LazyRow(
			modifier = Modifier
				.fillMaxWidth()
				.padding(vertical = 8.dp),
			horizontalArrangement = Arrangement.spacedBy(8.dp)
		) {
			items(animeList) { anime ->
				ListItemRow(
					node = anime.node,
					onItemClick = {
						navController.navigate(Screen.DetailScreen.route + "/${ScreenType.ANIME.name}/${anime.node.id}")
					}
				)
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
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