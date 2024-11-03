package com.farukaygun.yorozuyalist.presentation.user_list.views

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.farukaygun.yorozuyalist.domain.models.enums.MyListMediaStatus
import com.farukaygun.yorozuyalist.presentation.Screen
import com.farukaygun.yorozuyalist.presentation.composables.NoDataView
import com.farukaygun.yorozuyalist.presentation.composables.OnBottomReached
import com.farukaygun.yorozuyalist.presentation.composables.UserListItemColumn
import com.farukaygun.yorozuyalist.presentation.composables.shimmer_effect.ShimmerEffectVerticalList
import com.farukaygun.yorozuyalist.presentation.user_list.UserListEvent
import com.farukaygun.yorozuyalist.presentation.user_list.UserListViewModel
import com.farukaygun.yorozuyalist.util.ScreenType
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Composable
fun UserListScreen(
	navController: NavController,
	viewModel: UserListViewModel = koinViewModel()
) {
	val state = viewModel.state.value
	Column(
		modifier = Modifier
			.padding(16.dp)
	) {
		MyListStatusFilterChips(viewModel)

		if (!state.isLoading && state.userList?.data?.isNotEmpty() == true) {
			UserList(
				navController = navController,
				data = state.userList.data,
				viewModel = viewModel,
			)
		} else if (!state.isLoading && state.userList?.data?.isEmpty() == true) {
			NoDataView()
		} else {
			ShimmerEffectVerticalList()
		}
	}

	if (state.error.isNotEmpty())
		Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_SHORT).show()
}

@Composable
fun MyListStatusFilterChips(
	viewModel: UserListViewModel,
) {
	var statusFilter by rememberSaveable { mutableStateOf<MyListMediaStatus?>(null) }

	val statuses = buildList {
		if (viewModel.state.value.type == ScreenType.ANIME) {
			addAll(listOf(MyListMediaStatus.WATCHING, MyListMediaStatus.PLAN_TO_WATCH))
		} else {
			addAll(listOf(MyListMediaStatus.READING, MyListMediaStatus.PLAN_TO_READ))
		}
		addAll(listOf(MyListMediaStatus.COMPLETED, MyListMediaStatus.ON_HOLD, MyListMediaStatus.DROPPED))
	}

	fun toggleStatusFilter(status: MyListMediaStatus) {
		statusFilter = if (statusFilter?.formatForApi() == status.formatForApi()) null else status
		viewModel.onEvent(UserListEvent.FilterList(statusFilter))
	}

	fun getStatusIcon(status: MyListMediaStatus, isSelected: Boolean): Int {
		return if (isSelected) {
			R.drawable.close_16px
		} else {
			when (status) {
				MyListMediaStatus.WATCHING, MyListMediaStatus.READING -> R.drawable.play_circle_24px
				MyListMediaStatus.PLAN_TO_WATCH, MyListMediaStatus.PLAN_TO_READ -> R.drawable.schedule_24px
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
			val selected = statusFilter?.formatForApi() == status.formatForApi()
			FilterChip(
				onClick = { toggleStatusFilter(status) },
				label = { Text(status.format()) },
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

@Composable
fun UserList(
	navController: NavController,
	data: List<Data>,
	viewModel: UserListViewModel
) {
	val listState = rememberLazyListState()
	listState.OnBottomReached(buffer = 10) {
		viewModel.onEvent(UserListEvent.LoadMore)
	}

	LazyColumn(
		state = listState,
		modifier = Modifier.padding(16.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {

		items(data) { media ->
			UserListItemColumn(data = media, onItemClick = {
				if (viewModel.state.value.type == ScreenType.ANIME)
					navController.navigate(Screen.DetailScreen.route + "/${ScreenType.ANIME}/${media.node.id}")
				else navController.navigate(Screen.DetailScreen.route + "/${ScreenType.MANGA}/${media.node.id}")
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
		UserListScreen(navController = rememberNavController())
	}
}