package com.farukaygun.yorozuyalist.presentation

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.farukaygun.yorozuyalist.presentation.calendar.views.CalendarScreen
import com.farukaygun.yorozuyalist.presentation.composables.bottom_nav_bar.BottomNavBar
import com.farukaygun.yorozuyalist.presentation.composables.bottom_nav_bar.rememberBottomAppBarState
import com.farukaygun.yorozuyalist.presentation.composables.search_bar.SearchBar
import com.farukaygun.yorozuyalist.presentation.composables.search_bar.rememberSearchBarState
import com.farukaygun.yorozuyalist.presentation.detail.views.DetailScreen
import com.farukaygun.yorozuyalist.presentation.grid_list.views.GridListScreen
import com.farukaygun.yorozuyalist.presentation.home.views.HomeScreen
import com.farukaygun.yorozuyalist.presentation.login.LoginEvent
import com.farukaygun.yorozuyalist.presentation.login.LoginViewModel
import com.farukaygun.yorozuyalist.presentation.login.views.LoginScreen
import com.farukaygun.yorozuyalist.presentation.profile.views.ProfileScreen
import com.farukaygun.yorozuyalist.presentation.search.views.SearchScreen
import com.farukaygun.yorozuyalist.presentation.user_list.views.UserListScreen
import com.farukaygun.yorozuyalist.ui.theme.AppTheme
import com.farukaygun.yorozuyalist.util.enums.ScrollState
import org.koin.android.ext.android.inject
import kotlin.math.abs

class MainActivity : ComponentActivity() {
	private val loginViewModel: LoginViewModel by inject()

	@OptIn(ExperimentalMaterial3Api::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge(
			statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
			navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT)
		)

		setContent {
			AppTheme {
				val navController = rememberNavController()
				val searchBarState = rememberSearchBarState(navController = navController)
				val bottomAppBarState = rememberBottomAppBarState(navController = navController)
				val topAppBarScrollBehavior =
					TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
				val bottomAppBarScrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
				var currentListState by remember { mutableStateOf<LazyListState?>(null) }
				var isScaffoldBarVisible by remember { mutableStateOf(true) }
				var scrollState by remember { mutableStateOf(ScrollState.IDLE) }
				var accumulatedScroll = 0f
				val threshold = 5f

				val nestedScrollConnection = remember {
					object : NestedScrollConnection {
						override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
							currentListState?.takeIf {
								it.firstVisibleItemIndex == 0 && it.firstVisibleItemScrollOffset == 0
							}?.let {
								isScaffoldBarVisible = true
								accumulatedScroll = 0f
								scrollState = ScrollState.IDLE
								return Offset.Zero
							}
							
							currentListState?.takeIf {
								it.layoutInfo.visibleItemsInfo.lastOrNull()?.index == it.layoutInfo.totalItemsCount - 1
							}?.let {
								accumulatedScroll = 0f
								scrollState = ScrollState.IDLE
								return Offset.Zero
							}
							
							val currentScrollDirection = when {
								available.y < -1f -> ScrollState.SCROLLING_DOWN
								available.y > 1f -> ScrollState.SCROLLING_UP
								else -> ScrollState.IDLE
							}
							
							if (scrollState != currentScrollDirection && currentScrollDirection != ScrollState.IDLE) {
								accumulatedScroll = 0f
								scrollState = currentScrollDirection
							}
							
							if (currentScrollDirection != ScrollState.IDLE) {
								accumulatedScroll += abs(available.y)
							}

							return when {
								scrollState == ScrollState.SCROLLING_DOWN &&
										isScaffoldBarVisible &&
										accumulatedScroll >= threshold -> {
									isScaffoldBarVisible = false
									accumulatedScroll = 0f
									available
								}
								
								scrollState == ScrollState.SCROLLING_UP &&
										!isScaffoldBarVisible &&
										accumulatedScroll >= threshold -> {
									isScaffoldBarVisible = true
									accumulatedScroll = 0f
									available
								}

								else -> Offset.Zero
							}
						}
					}
				}

				navController.addOnDestinationChangedListener { _, _, _ ->
					isScaffoldBarVisible = true
					accumulatedScroll = 0f
				}

				Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
					AnimatedVisibility(
						modifier = Modifier.statusBarsPadding(),
						visible = searchBarState.isVisible && isScaffoldBarVisible,
						enter = expandVertically(
							expandFrom = Alignment.Bottom,
							animationSpec = tween(durationMillis = 200)
						),
						exit = shrinkVertically(
							shrinkTowards = Alignment.Top,
							animationSpec = tween(durationMillis = 200)
						)
					) {
						TopAppBar(
							title = { },
							scrollBehavior = topAppBarScrollBehavior,
							actions = {
								Box(modifier = Modifier.fillMaxWidth()) {
									SearchBar(
										navController = navController
									)
								}
							},
							colors = TopAppBarDefaults.topAppBarColors(
								containerColor = MaterialTheme.colorScheme.background,
								scrolledContainerColor = MaterialTheme.colorScheme.background
							)
						)
					}
				}, bottomBar = {
					AnimatedVisibility(
						modifier = Modifier.navigationBarsPadding(),
						visible = bottomAppBarState.isEnabled && isScaffoldBarVisible,
						enter = expandVertically(
							expandFrom = Alignment.Top,
							animationSpec = tween(durationMillis = 200)
						),
						exit = shrinkVertically(
							shrinkTowards = Alignment.Top,
							animationSpec = tween(durationMillis = 200)
						)
					) {
						BottomAppBar(
							scrollBehavior = bottomAppBarScrollBehavior, actions = {
								Box(modifier = Modifier.fillMaxWidth()) {
									BottomNavBar(
										navController = navController,
										bottomAppBarState = bottomAppBarState
									)
								}
							})
					}
				}) { paddingValues ->
					NavHost(
						modifier = Modifier.padding(paddingValues),
						navController = navController,
						startDestination = if (loginViewModel.isLoggedIn()) Screen.HomeScreen.route else Screen.LoginScreen.route,
						enterTransition = {
							fadeIn() + slideIntoContainer(
								towards = AnimatedContentTransitionScope.SlideDirection.Start,
								animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
							)
						},
						exitTransition = {
							fadeOut() + slideOutOfContainer(
								towards = AnimatedContentTransitionScope.SlideDirection.End,
								animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
							)
						},
						popEnterTransition = {
							fadeIn()
						},
						popExitTransition = {
							fadeOut() + slideOutOfContainer(
								towards = AnimatedContentTransitionScope.SlideDirection.End,
								animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
							)
						}) {
						composable(
							route = Screen.LoginScreen.route,
						) {
							LoginScreen(
								navController = navController, viewModel = loginViewModel
							)
						}

						composable(
							route = Screen.HomeScreen.route,
							enterTransition = { fadeIn() },
							exitTransition = { fadeOut() },
							popEnterTransition = { fadeIn() },
							popExitTransition = { fadeOut() },
						) {
							BackHandler(true) {
								Log.d("MainActivity", "Navigation Home: Back Pressed")
							}

							HomeScreen(
								navController = navController,
							)
						}

						composable(
							route = "${Screen.UserAnimeListScreen.route}/{SCREEN_TYPE_PARAM}",
							enterTransition = { fadeIn() },
							exitTransition = { fadeOut() },
							popEnterTransition = { fadeIn() },
							popExitTransition = { fadeOut() },
							arguments = listOf(
								navArgument("SCREEN_TYPE_PARAM") {
									type = NavType.StringType
								})
						) {
							UserListScreen(
								navController = navController,
								nestedScrollConnection = nestedScrollConnection,
								onListStateChanged = { listState ->
									currentListState = listState
								}
							)
						}

						composable(
							route = Screen.UserMangaListScreen.route + "/{SCREEN_TYPE_PARAM}",
							enterTransition = { fadeIn() },
							exitTransition = { fadeOut() },
							popEnterTransition = { fadeIn() },
							popExitTransition = { fadeOut() },
							arguments = listOf(
								navArgument("SCREEN_TYPE_PARAM") {
									type = NavType.StringType
								})
						) {
							UserListScreen(
								navController = navController,
								nestedScrollConnection = nestedScrollConnection,
								onListStateChanged = { listState ->
									currentListState = listState
								}
							)
						}

						composable(
							route = Screen.ProfileScreen.route,
							enterTransition = { fadeIn() },
							exitTransition = { fadeOut() },
							popEnterTransition = { fadeIn() },
							popExitTransition = { fadeOut() },
						) {
							ProfileScreen()
						}

						composable(
							route = Screen.SearchScreen.route
						) {
							SearchScreen(navController = navController)
						}

						composable(
							route = Screen.GridListScreen.route + "/{SCREEN_TYPE_PARAM}",
							arguments = listOf(
								navArgument("SCREEN_TYPE_PARAM") {
									type = NavType.StringType
								})
						) {
							val type = it.arguments?.getString("SCREEN_TYPE_PARAM") ?: ""
							GridListScreen(
								navController = navController,
								type = type
							)
						}

						composable(
							route = Screen.DetailScreen.route + "/{SCREEN_TYPE_PARAM}/{MEDIA_ID_PARAM}",
							arguments = listOf(navArgument("SCREEN_TYPE_PARAM") {
								type = NavType.StringType
							}, navArgument("MEDIA_ID_PARAM") {
								type = NavType.StringType
							})
						) {
							val type = it.arguments?.getString("SCREEN_TYPE_PARAM") ?: ""
							DetailScreen(
								navController = navController, type = type
							)
						}

						composable(
							route = Screen.CalendarScreen.route,
							enterTransition = { fadeIn() },
							exitTransition = { fadeOut() },
							popEnterTransition = { fadeIn() },
							popExitTransition = { fadeOut() }
						) {
							CalendarScreen(
								navController = navController,
								nestedScrollConnection = nestedScrollConnection,
								onListStateChanged = { listState ->
									currentListState = listState
								}
							)
						}
					}
				}
			}
		}
	}

	override fun onNewIntent(intent: Intent) {
		super.onNewIntent(intent)
		loginViewModel.onEvent(LoginEvent.ParseIntentData(applicationContext, intent))
	}
}
