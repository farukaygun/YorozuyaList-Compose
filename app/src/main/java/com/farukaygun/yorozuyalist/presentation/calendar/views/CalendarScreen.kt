package com.farukaygun.yorozuyalist.presentation.calendar.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.farukaygun.yorozuyalist.data.di.apiServiceModule
import com.farukaygun.yorozuyalist.data.di.repositoryModule
import com.farukaygun.yorozuyalist.data.di.useCaseModule
import com.farukaygun.yorozuyalist.data.di.viewModelModule
import com.farukaygun.yorozuyalist.presentation.Screen
import com.farukaygun.yorozuyalist.presentation.calendar.CalendarState
import com.farukaygun.yorozuyalist.presentation.calendar.CalendarViewModel
import com.farukaygun.yorozuyalist.presentation.composables.ListItemCalenderColumn
import com.farukaygun.yorozuyalist.presentation.composables.shimmer_effect.ShimmerEffect
import com.farukaygun.yorozuyalist.presentation.composables.shimmer_effect.ShimmerEffectVerticalList
import com.farukaygun.yorozuyalist.util.Calendar
import com.farukaygun.yorozuyalist.util.enums.ScreenType
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Composable
fun CalendarScreen(
	navController: NavController,
	viewModel: CalendarViewModel = koinViewModel(),
	nestedScrollConnection: NestedScrollConnection,
	onListStateChanged: (LazyListState) -> Unit
) {
	val state = viewModel.state.value
	val listState = rememberLazyListState()

	LaunchedEffect(listState) {
		onListStateChanged(listState)
	}

	Column(
		modifier = Modifier.padding(horizontal = 16.dp)
	) {
		
		if (!state.isLoading && state.animeWeeklyList.isNotEmpty()) {
			WeeklyTabView(
				navController = navController,
				state = state,
				listState = listState,
				nestedScrollConnection = nestedScrollConnection
			)
		} else {
			ShimmerEffectCalendarScreen()
		}
	}
}

@Composable
fun WeeklyTabView(
	navController: NavController,
	state: CalendarState,
	listState: LazyListState,
	nestedScrollConnection: NestedScrollConnection
) {
	var selectedDay by remember { mutableStateOf(Calendar.WeekDays.entries[0]) }

	ScrollableTabRow(
		selectedTabIndex = Calendar.WeekDays.entries.indexOf(selectedDay),
		modifier = Modifier.fillMaxWidth(),
		edgePadding = 0.dp
	) {
		Calendar.WeekDays.entries.forEach { day ->
			Tab(
				selected = day == selectedDay,
				onClick = { selectedDay = day },
				text = { Text(text = day.displayName) },
				modifier = Modifier.padding(vertical = 8.dp)
			)
		}
	}

	if (state.animeWeeklyList.containsKey(selectedDay)) {
		LazyColumn(
			state = listState,
			modifier = Modifier
				.fillMaxWidth()
				.nestedScroll(nestedScrollConnection)
		) {
			state.animeWeeklyList[selectedDay]?.let { data ->
				items(data) { media ->
					ListItemCalenderColumn(data = media, onItemClick = {
						navController.navigate(Screen.DetailScreen.route + "/${ScreenType.ANIME}/${media.node.id}")
					})
				}
			}
		}
	}
}

@Composable
fun ShimmerEffectCalendarScreen() {
	Column {
		ShimmerEffect(
			modifier = Modifier
				.clip(RoundedCornerShape(10.dp))
				.size(400.dp, 65.dp)
		)
		
		ShimmerEffectVerticalList()
	}
}

@Composable
@Preview
fun CalendarScreenPreview() {
	val context = LocalContext.current
	
	KoinApplication(application = {
		androidContext(context)
		modules(
			viewModelModule, repositoryModule, useCaseModule, apiServiceModule
		)
	}) {
		CalendarScreen(
			navController = rememberNavController(),
			nestedScrollConnection = rememberNestedScrollInteropConnection(),
			onListStateChanged = {}
		)
	}
}