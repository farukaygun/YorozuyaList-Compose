package com.farukaygun.yorozuyalist.presentation.composables.bottom_nav_bar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.farukaygun.yorozuyalist.presentation.Screen
import com.farukaygun.yorozuyalist.presentation.Screen.HomeScreen.getScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BottomAppBarState(
	navController: NavController,
	scope: CoroutineScope
) {
	var currentScreen by mutableStateOf<Screen?>(null)

	val items = listOf(
		BottomAppBarItem.Home,
		BottomAppBarItem.Calendar,
		BottomAppBarItem.UserAnimeList,
		BottomAppBarItem.UserMangaList,
		BottomAppBarItem.Profile
	)

	val isEnabled: Boolean
		get() = currentScreen?.isBottomAppBarVisible == true

	init {
		navController.currentBackStackEntryFlow
			.distinctUntilChanged()
			.onEach { backStackEntry ->
				val route = backStackEntry.destination.route
				currentScreen = getScreen(route)
			}
			.launchIn(scope)
	}
}

@Composable
fun rememberBottomAppBarState(
	navController: NavController,
	scope: CoroutineScope = rememberCoroutineScope()
) = remember {
	BottomAppBarState(
		navController,
		scope
	)
}