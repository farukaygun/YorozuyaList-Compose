package com.farukaygun.yorozuyalist.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
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
import com.farukaygun.yorozuyalist.presentation.common.rememberAppBarState
import com.farukaygun.yorozuyalist.presentation.composables.app_bar.AppBar
import com.farukaygun.yorozuyalist.presentation.composables.bottom_nav_bar.BottomNavBar
import com.farukaygun.yorozuyalist.presentation.composables.bottom_nav_bar.rememberBottomNavBarState
import com.farukaygun.yorozuyalist.presentation.home.views.HomeScreen
import com.farukaygun.yorozuyalist.presentation.login.LoginViewModel
import com.farukaygun.yorozuyalist.presentation.login.views.LoginScreen
import com.farukaygun.yorozuyalist.presentation.profile.views.ProfileScreen
import com.farukaygun.yorozuyalist.presentation.search.views.SearchScreen
import com.farukaygun.yorozuyalist.presentation.user_list.views.UserListScreen
import com.farukaygun.yorozuyalist.ui.theme.AppTheme
import com.farukaygun.yorozuyalist.util.Constants.USER_LIST_TYPE_PARAM
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
					val searchBarState = rememberAppBarState(navController = navController)
					val bottomNavBarState = rememberBottomNavBarState(navController = navController)

					Scaffold(
						topBar = {
							AppBar(
								navController = navController,
								isVisible = searchBarState.isVisible,
							)
						},
						bottomBar = {
							BottomNavBar(
								navController = navController,
								bottomNavBarState = bottomNavBarState
							)
						}
					) { padding ->
						NavHost(
							navController = navController,
							startDestination = Screen.HomeScreen.route,
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
								popEnterTransition = { fadeIn() },
								popExitTransition = { fadeOut() }
							) {
								BackHandler(true) {
									Log.d("MainActivity", "Navigation Home: Back Pressed")
								}

								HomeScreen(navController = navController)
							}

							composable(
								route = Screen.UserListScreen.route + "/{${USER_LIST_TYPE_PARAM}}",
								arguments = listOf(
									navArgument(USER_LIST_TYPE_PARAM) {
										type = NavType.StringType
									}
								),
								enterTransition = { fadeIn() },
								exitTransition = { fadeOut() }
							) {
								val type = it.arguments?.getString(USER_LIST_TYPE_PARAM) ?: ""
								UserListScreen(navController = navController, type = type)
							}

							composable(
								route = Screen.ProfileScreen.route,
								enterTransition = { fadeIn() },
								exitTransition = { fadeOut() }
							) {
								ProfileScreen(navController = navController)
							}

							composable(
								route = Screen.SearchScreen.route,
								enterTransition = { fadeIn() },
								exitTransition = { fadeOut() }
							) {
								SearchScreen(navController = navController)
							}
						}
					}
				}
			}
		}
	}

	override fun onNewIntent(intent: Intent) {
		super.onNewIntent(intent)
		loginViewModel.parseIntentData(applicationContext, intent)
	}
}
