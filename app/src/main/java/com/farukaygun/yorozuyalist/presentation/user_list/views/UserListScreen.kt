package com.farukaygun.yorozuyalist.presentation.user_list.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.farukaygun.yorozuyalist.presentation.user_list.UserListEvent
import com.farukaygun.yorozuyalist.presentation.user_list.UserListViewModel
import com.farukaygun.yorozuyalist.util.UserListType
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication


// User list screen for user manga and anime lists.
@Composable
fun UserListScreen(
	navController: NavController,
	viewModel: UserListViewModel = koinViewModel(),
	type: String
) {
	val state = viewModel.state.value

	LaunchedEffect(Unit) {
		viewModel.state.value = viewModel.state.value.copy(type = UserListType.valueOf(type))
		viewModel.onEvent(UserListEvent.InitRequestChain)
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
		state.userList?.data?.let {
			UserList(
				data = it,
				viewModel = viewModel,
			)
		}
	}
}

@Composable
fun UserList(
	data: List<Data>,
	viewModel: UserListViewModel
) {
	val listState = rememberLazyListState()
	listState.OnBottomReached(buffer = 10) {
		viewModel.onEvent(UserListEvent.LoadMore)
	}

	Surface {
		Box {
			LazyColumn(
				state = listState,
				modifier = Modifier.padding(8.dp),
				verticalArrangement = Arrangement.SpaceBetween
			) {

				items(data) { anime ->
					UserListItemColumn(data = anime, onItemClick = {
						// navController.navigate(Screen.AnimeDetailScreen.route+"/${anime.node.id}")
					})
				}

				if (viewModel.state.value.isLoadingMore) {
					item {
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

@Composable
@Preview
fun AnimeListScreenPreview() {
	val context = LocalContext.current

	KoinApplication(application = {
		androidContext(context)
		modules(
			viewModelModule, repositoryModule, useCaseModule, apiServiceModule
		)
	}) {
		UserListScreen(navController = rememberNavController(), type = UserListType.ANIME_LIST.name)
	}
}