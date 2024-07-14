package com.farukaygun.yorozuyalist.presentation.grid_list.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.farukaygun.yorozuyalist.domain.model.Data
import com.farukaygun.yorozuyalist.presentation.composables.GridListItem
import com.farukaygun.yorozuyalist.presentation.composables.views.OnBottomReached
import com.farukaygun.yorozuyalist.presentation.grid_list.GridListEvent
import com.farukaygun.yorozuyalist.presentation.grid_list.GridListViewModel
import com.farukaygun.yorozuyalist.util.GridListType
import org.koin.androidx.compose.koinViewModel

@Composable
fun GridListScreen(
	navController: NavController,
	viewModel: GridListViewModel = koinViewModel(),
	type: String
) {
	val state = viewModel.state.value

	LaunchedEffect(Unit) {
		viewModel.state.value = viewModel.state.value.copy(type = GridListType.valueOf(type))
		viewModel.onEvent(GridListEvent.InitRequestChain)
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
		state.gridList?.data?.let {
			GridList(
				data = it,
				viewModel = viewModel
			)
		}
	}
}

@Composable
fun GridList(
	data: List<Data>,
	viewModel: GridListViewModel,
) {
	val listState = rememberLazyGridState()
	listState.OnBottomReached(buffer = 10) {
		viewModel.onEvent(GridListEvent.LoadMore)
	}

	Surface {
		Column(modifier = Modifier.padding(16.dp)) {
			LazyVerticalGrid(
				state = listState,
				columns = GridCells.FixedSize(110.dp),
				horizontalArrangement = Arrangement.SpaceBetween,
			) {
				items(data) { anime ->
					GridListItem(data = anime, onItemClick = {
						// navController.navigate(Screen.AnimeDetailScreen.route+"/${anime.node.id}")
					})
				}

				if (viewModel.state.value.isLoadingMore) {
					item(
						span = { GridItemSpan(maxLineSpan) }
					) {
						Column(
							modifier = Modifier.padding(16.dp),
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