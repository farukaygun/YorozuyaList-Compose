package com.farukaygun.yorozuyalist.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.farukaygun.yorozuyalist.presentation.composables.bottom_nav_bar.BottomNavBar
import com.farukaygun.yorozuyalist.presentation.composables.bottom_nav_bar.rememberBottomNavBarState
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
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
	private val loginViewModel: LoginViewModel by inject()

	@OptIn(ExperimentalMaterial3Api::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			AppTheme {
				val navController = rememberNavController()
				val searchBarState = rememberSearchBarState(navController = navController)
				val bottomNavBarState = rememberBottomNavBarState(navController = navController)
				val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
				val bottomAppBarScrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()

				navController.addOnDestinationChangedListener { _, _, _ ->
					topAppBarScrollBehavior.state.contentOffset = 0f
					topAppBarScrollBehavior.state.heightOffset = 0f

					bottomAppBarScrollBehavior.state.contentOffset = 0f
					bottomAppBarScrollBehavior.state.heightOffset = 0f
				}

				Scaffold(
					modifier = Modifier.fillMaxSize()
						.navigationBarsPadding()
						.statusBarsPadding(),
					topBar = {
						AnimatedVisibility(
							visible = searchBarState.isVisible,
							enter = expandVertically(
								expandFrom = Alignment.Top,
								animationSpec = tween(durationMillis = 350)
							),
							exit = shrinkVertically(
								shrinkTowards = Alignment.Top,
								animationSpec = tween(durationMillis = 350)
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
					},
					bottomBar = {
						AnimatedVisibility(
							visible = bottomNavBarState.isVisible,
							enter = expandVertically(
								expandFrom = Alignment.Top,
								animationSpec = tween(durationMillis = 350)
							),
							exit = shrinkVertically(
								shrinkTowards = Alignment.Top,
								animationSpec = tween(durationMillis = 350)
							)
						) {
							BottomAppBar(
								scrollBehavior = bottomAppBarScrollBehavior,
								actions = {
									Box(modifier = Modifier.fillMaxWidth()) {
										BottomNavBar(
											navController = navController,
											bottomNavBarState = bottomNavBarState
										)
									}
								}
							)
						}
					}
				) { paddingValues ->

					NavHost(
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
						},
						modifier = Modifier.padding(paddingValues)
					) {
						composable(
							route = Screen.LoginScreen.route,
						) {
							LoginScreen(
								navController = navController,
								viewModel = loginViewModel
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
								}
							)
						) {
							UserListScreen(
								navController = navController,
								topAppBarScrollBehavior = topAppBarScrollBehavior,
								bottomAppBarScrollBehavior = bottomAppBarScrollBehavior
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
								}
							)
						) {
							UserListScreen(
								navController = navController,
								topAppBarScrollBehavior = topAppBarScrollBehavior,
								bottomAppBarScrollBehavior = bottomAppBarScrollBehavior
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
								}
							)
						) {
							val type = it.arguments?.getString("SCREEN_TYPE_PARAM") ?: ""
							GridListScreen(
								navController = navController,
								type = type,
								bottomAppBarScrollBehavior = bottomAppBarScrollBehavior
							)
						}

						composable(
							route = Screen.DetailScreen.route + "/{SCREEN_TYPE_PARAM}/{MEDIA_ID_PARAM}",
							arguments = listOf(
								navArgument("SCREEN_TYPE_PARAM") {
									type = NavType.StringType
								},
								navArgument("MEDIA_ID_PARAM") {
									type = NavType.StringType
								}
							)
						) {
							val type = it.arguments?.getString("SCREEN_TYPE_PARAM") ?: ""
							DetailScreen(
								navController = navController,
								type = type
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
