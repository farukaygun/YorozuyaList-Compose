package com.farukaygun.yorozuyalist.presentation.user_list.views

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.data.di.apiServiceModule
import com.farukaygun.yorozuyalist.data.di.repositoryModule
import com.farukaygun.yorozuyalist.data.di.useCaseModule
import com.farukaygun.yorozuyalist.data.di.viewModelModule
import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.enums.MyListMediaStatus
import com.farukaygun.yorozuyalist.presentation.Screen
import com.farukaygun.yorozuyalist.presentation.composables.NoDataView
import com.farukaygun.yorozuyalist.presentation.composables.OnBottomReached
import com.farukaygun.yorozuyalist.presentation.composables.UserListItemColumn
import com.farukaygun.yorozuyalist.presentation.composables.shimmer_effect.ShimmerEffectVerticalList
import com.farukaygun.yorozuyalist.presentation.search.views.SearchScreen
import com.farukaygun.yorozuyalist.presentation.user_list.UserListEvent
import com.farukaygun.yorozuyalist.presentation.user_list.UserListViewModel
import com.farukaygun.yorozuyalist.util.enums.ScreenType
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration

@Composable
fun UserListScreen(
	navController: NavController,
	viewModel: UserListViewModel = koinViewModel(),
	nestedScrollConnection: NestedScrollConnection,
	onListStateChanged: (LazyListState) -> Unit,
	isTopBarVisible: Boolean = true
) {
	val state by viewModel.state.collectAsStateWithLifecycle()
	val userList = state.userList
	val listState = rememberLazyListState()

	LaunchedEffect(listState) {
		onListStateChanged(listState)
	}

	Column {
		AnimatedVisibility(
			visible = isTopBarVisible,
			enter = expandVertically(
				expandFrom = Alignment.Bottom,
				animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
			),
			exit = shrinkVertically(
				shrinkTowards = Alignment.Bottom,
				animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
			)
		) {
			Box(modifier = Modifier.statusBarsPadding()) {
				SearchScreen(navController = navController)
			}
		}

		Column(
			modifier = Modifier.padding(horizontal = 16.dp),
			verticalArrangement = Arrangement.SpaceBetween
		) {
			MyListStatusFilterChips(viewModel)

			if (!state.isLoading && userList?.data?.isNotEmpty() == true) {
				UserList(
					navController = navController,
					data = userList.data,
					viewModel = viewModel,
					listState = listState,
					nestedScrollConnection = nestedScrollConnection
				)
			} else if (!state.isLoading && userList?.data?.isEmpty() == true) {
				NoDataView()
			} else {
				ShimmerEffectVerticalList()
			}
		}
	}

	state.error?.let { error ->
		Toast.makeText(LocalContext.current, error.toMessage(), Toast.LENGTH_SHORT).show()
	}
}


@Composable
fun MyListStatusFilterChips(
	viewModel: UserListViewModel,
) {
	var statusFilter by rememberSaveable { mutableStateOf<MyListMediaStatus?>(null) }
	val vmState by viewModel.state.collectAsStateWithLifecycle()

	val statuses = buildList {
		if (vmState.type == ScreenType.ANIME) {
			addAll(listOf(MyListMediaStatus.WATCHING, MyListMediaStatus.PLAN_TO_WATCH))
		} else {
			addAll(listOf(MyListMediaStatus.READING, MyListMediaStatus.PLAN_TO_READ))
		}
		addAll(
			listOf(
				MyListMediaStatus.COMPLETED,
				MyListMediaStatus.ON_HOLD,
				MyListMediaStatus.DROPPED
			)
		)
	}

	fun toggleStatusFilter(status: MyListMediaStatus) {
		statusFilter = if (statusFilter?.apiName == status.apiName) null else status
		viewModel.onEvent(UserListEvent.FilterList(statusFilter))
	}

	fun getStatusIcon(status: MyListMediaStatus, isSelected: Boolean): Int {
		return if (isSelected) {
			R.drawable.close_16px
		} else {
			when (status) {
				MyListMediaStatus.WATCHING, MyListMediaStatus.READING -> R.drawable.play_circle_24px
				MyListMediaStatus.PLAN_TO_WATCH, MyListMediaStatus.PLAN_TO_READ -> R.drawable.schedule_16px
				MyListMediaStatus.COMPLETED -> R.drawable.check_circle_24px
				MyListMediaStatus.ON_HOLD -> R.drawable.pause_circle_24px
				MyListMediaStatus.DROPPED -> R.drawable.delete_24px
			}
		}
	}

	Row(
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		modifier = Modifier.horizontalScroll(rememberScrollState())
	) {
		statuses.forEach { status ->
			val selected = statusFilter?.apiName == status.apiName
			FilterChip(
				onClick = { toggleStatusFilter(status) },
				label = { Text(status.displayName) },
				selected = selected,
				leadingIcon = {
					AnimatedContent(
						targetState = selected,
						transitionSpec = {
							fadeIn() togetherWith fadeOut()
						}, label = ""
					) { isSelected ->
						Icon(
							painterResource(id = getStatusIcon(status, isSelected)),
							contentDescription = "Status icon",
						)
					}
				}
			)
		}
	}
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun UserList(
	navController: NavController,
	data: List<Data>,
	viewModel: UserListViewModel,
	listState: LazyListState,
	nestedScrollConnection: NestedScrollConnection
) {
	val userListState by viewModel.state.collectAsStateWithLifecycle()

	listState.OnBottomReached(buffer = 10) {
		viewModel.onEvent(UserListEvent.LoadMore)
	}

	LazyColumn(
		state = listState,
		modifier = Modifier
			.fillMaxWidth()
			.nestedScroll(nestedScrollConnection)
	) {
		items(data) { media ->
			UserListItemColumn(data = media, onItemClick = {
				if (userListState.type == ScreenType.ANIME)
					navController.navigate(Screen.DetailScreen.route + "/${ScreenType.ANIME}/${media.node.id}")
				else navController.navigate(Screen.DetailScreen.route + "/${ScreenType.MANGA}/${media.node.id}")
			})
		}

		if (userListState.isLoadingMore) {
			item {
				Column(
					modifier = Modifier.padding(16.dp),
					verticalArrangement = Arrangement.Center,
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					CircularWavyProgressIndicator()
				}
			}
		}
	}
}

@Composable
@Preview
fun AnimeListScreenPreview() {
    val context = LocalContext.current

    KoinApplication(configuration = koinConfiguration(declaration = {
        androidContext(context)
        modules(
            viewModelModule, repositoryModule, useCaseModule, apiServiceModule
        )
    }), content = {
        UserListScreen(
            navController = rememberNavController(),
            nestedScrollConnection = rememberNestedScrollInteropConnection(),
            onListStateChanged = {}
        )
    })
}