package com.farukaygun.yorozuyalist.presentation.anime_list.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.farukaygun.yorozuyalist.data.di.apiServiceModule
import com.farukaygun.yorozuyalist.data.di.repositoryModule
import com.farukaygun.yorozuyalist.data.di.useCaseModule
import com.farukaygun.yorozuyalist.data.di.viewModelModule
import com.farukaygun.yorozuyalist.domain.model.Data
import com.farukaygun.yorozuyalist.presentation.composables.UserListItemColumn
import com.farukaygun.yorozuyalist.presentation.composables.views.OnBottomReached
import com.farukaygun.yorozuyalist.presentation.manga_list.MangaListEvent
import com.farukaygun.yorozuyalist.presentation.manga_list.MangaListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Composable
fun MangaListScreen(
	navController: NavController,
	viewModel: MangaListViewModel = koinViewModel()
) {
	val state = viewModel.state.value

	LaunchedEffect(Unit) {
		viewModel.onEvent(MangaListEvent.InitRequestChain)
	}

	if (state.isLoading) {
		Box(
			modifier = Modifier
				.fillMaxSize(),
			contentAlignment = Alignment.Center
		) {
			CircularProgressIndicator(
				modifier = Modifier
					.wrapContentHeight()
					.align(Alignment.Center)
			)
		}
	}

	Column {
		state.userMangaList?.data?.let {
			MangaList(data = it, viewModel = viewModel)
		}
	}
}

@Composable
fun MangaList(
	data: List<Data>, viewModel: MangaListViewModel
) {
	val listState = rememberLazyListState()
	listState.OnBottomReached(buffer = 10) {
		viewModel.onEvent(MangaListEvent.LoadMore)
	}

	Surface(
		modifier = Modifier.fillMaxSize()
	) {
		Box(
			modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
		) {
			LazyColumn(
				state = listState, modifier = Modifier
					.fillMaxWidth()
					.padding(8.dp)
			) {

				items(data) { anime ->
					UserListItemColumn(data = anime, onItemClick = {
						// navController.navigate(Screen.AnimeDetailScreen.route+"/${anime.node.id}")
					})
				}

				if (viewModel.state.value.isLoadingMore) {
					item {
						Column(
							modifier = Modifier.fillMaxWidth(),
							verticalArrangement = Arrangement.Center,
							horizontalAlignment = Alignment.CenterHorizontally
						) {
							CircularProgressIndicator()
						}
					}
				}
			}
		}
	}
}

@Composable
@Preview
fun MangaListScreenPreview() {
	val context = LocalContext.current

	KoinApplication(application = {
		androidContext(context)
		modules(
			viewModelModule, repositoryModule, useCaseModule, apiServiceModule
		)
	}) {
		MangaListScreen(
			navController = rememberNavController()
		)
	}
}