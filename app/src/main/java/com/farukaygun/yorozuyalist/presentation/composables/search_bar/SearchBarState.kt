package com.farukaygun.yorozuyalist.presentation.composables.search_bar

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

class SearchBarState(
	navController: NavController,
	scope: CoroutineScope
) {
	private var currentScreen by mutableStateOf<Screen?>(null)

	val isVisible: Boolean
		get() = currentScreen?.isSearchBarVisible == true

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
fun rememberSearchBarState(
	navController: NavController,
	scope: CoroutineScope = rememberCoroutineScope()
) = remember {
	SearchBarState(
		navController,
		scope
	)
}