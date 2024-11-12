package com.farukaygun.yorozuyalist.presentation.grid_list.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.presentation.Screen
import com.farukaygun.yorozuyalist.presentation.composables.GridListItem
import com.farukaygun.yorozuyalist.presentation.composables.GridListItemWithRank
import com.farukaygun.yorozuyalist.presentation.composables.OnBottomReached
import com.farukaygun.yorozuyalist.presentation.composables.shimmer_effect.ShimmerEffectGridList
import com.farukaygun.yorozuyalist.presentation.grid_list.GridListEvent
import com.farukaygun.yorozuyalist.presentation.grid_list.GridListViewModel
import com.farukaygun.yorozuyalist.util.GridListType
import com.farukaygun.yorozuyalist.util.ScreenType
import org.koin.androidx.compose.koinViewModel

@Composable
fun GridListScreen(
	navController: NavController,
	viewModel: GridListViewModel = koinViewModel(),
	type: String
) {
	val state = viewModel.state.value

	LaunchedEffect(Unit) {
		viewModel.updateType(GridListType.valueOf(type))
		viewModel.onEvent(GridListEvent.InitRequestChain)
	}

	Column(
		modifier = Modifier
			.padding(16.dp)
	) {
		if (!state.isLoading && state.gridList?.data?.isNotEmpty() == true) {
			GridList(
				navController = navController,
				data = state.gridList.data,
				viewModel = viewModel
			)
		} else {
			ShimmerEffectGridList()
		}
	}

	if (state.error.isNotEmpty())
		Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_SHORT).show()
}

@Composable
fun GridList(
	navController: NavController,
	data: List<Data>,
	viewModel: GridListViewModel,
) {
	val listState = rememberLazyGridState()
	listState.OnBottomReached(buffer = 10) {
		viewModel.onEvent(GridListEvent.LoadMore)
	}

	Box {
		LazyVerticalGrid(
			state = listState,
			columns = GridCells.Adaptive(minSize = 100.dp),
			horizontalArrangement = Arrangement.SpaceAround,
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			val type = viewModel.state.value.type
			items(data) { anime ->
				when (type) {
					GridListType.SUGGESTED_ANIME_LIST, GridListType.SEASONAL_ANIME_LIST -> {
						GridListItem(data = anime, onItemClick = {
							navController.navigate(Screen.DetailScreen.route + "/${ScreenType.ANIME.name}/${anime.node.id}")
						})
					}

					GridListType.RANKING_ANIME_LIST, GridListType.RANKING_MANGA_LIST -> {
						GridListItemWithRank(data = anime, onItemClick = {
							navController.navigate(Screen.DetailScreen.route + "/${ScreenType.ANIME.name}/${anime.node.id}")
						})
					}
				}
			}
		}
	}
}