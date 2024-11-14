package com.farukaygun.yorozuyalist.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.farukaygun.yorozuyalist.presentation.common.rememberSearchBarState
import com.farukaygun.yorozuyalist.presentation.composables.bottom_nav_bar.BottomNavBar
import com.farukaygun.yorozuyalist.presentation.composables.bottom_nav_bar.rememberBottomNavBarState
import com.farukaygun.yorozuyalist.presentation.composables.search_bar.SearchBar
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

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			AppTheme {
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					val navController = rememberNavController()
					val searchBarState = rememberSearchBarState(navController = navController)
					val bottomNavBarState = rememberBottomNavBarState(navController = navController)

					Scaffold(
						topBar = {
							SearchBar(
								navController = navController,
								isVisible = searchBarState.isVisible
							)
						},
						bottomBar = {
							BottomNavBar(
								navController = navController,
								bottomNavBarState = bottomNavBarState
							)
						},
					) { padding ->
						NavHost(
							navController = navController,
							startDestination = Screen.HomeScreen.route,
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
							modifier = Modifier.padding(padding),
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

								HomeScreen(navController = navController)
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
								UserListScreen(navController = navController)
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
								UserListScreen(navController = navController)
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
								GridListScreen(navController = navController, type = type)
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
	}

	override fun onNewIntent(intent: Intent) {
		super.onNewIntent(intent)
		loginViewModel.onEvent(LoginEvent.ParseIntentData(applicationContext, intent))
	}
}
