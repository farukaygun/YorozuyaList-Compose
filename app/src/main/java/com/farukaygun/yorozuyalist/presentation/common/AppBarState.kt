package com.farukaygun.yorozuyalist.presentation.common

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

class AppBarState(
	navController: NavController,
	scope: CoroutineScope
) {

	init {
		navController.currentBackStackEntryFlow
			.distinctUntilChanged()
			.onEach { backStackEntry ->
				val route = backStackEntry.destination.route
				currentScreen = getScreen(route)
			}
			.launchIn(scope)
	}
	var currentScreen by mutableStateOf<Screen?>(null)
		private set

	val isVisible: Boolean
		get() = currentScreen?.isAppBarVisible == true

	val title: String
		get() = currentScreen?.title.toString()
}

@Composable
fun rememberAppBarState(
	navController: NavController,
	scope: CoroutineScope = rememberCoroutineScope(),
) = remember {
	AppBarState(
		navController,
		scope
	)
}