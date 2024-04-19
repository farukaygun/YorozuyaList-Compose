package com.farukaygun.yorozuyalist.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.farukaygun.yorozuyalist.presentation.home.views.HomeScreen
import com.farukaygun.yorozuyalist.presentation.login.LoginViewModel
import com.farukaygun.yorozuyalist.presentation.login.views.LoginScreen
import com.farukaygun.yorozuyalist.ui.theme.YorozuyaListTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
	private val loginViewModel: LoginViewModel by inject()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			YorozuyaListTheme {
				// A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					val navController = rememberNavController()
					NavHost(
						navController = navController,
						startDestination = Screen.HomeScreen.route
					) {
						composable(route = Screen.LoginScreen.route) {
							LoginScreen(
								navController = navController,
								viewModel = loginViewModel
							)
						}
						composable(route = Screen.HomeScreen.route) {
							BackHandler(true) {
								Log.d("MainActivity", "Navigation Home: Back Pressed")
							}

							HomeScreen(navController = navController)
						}
					}
				}
			}
		}
	}

	override fun onNewIntent(intent: Intent?) {
		super.onNewIntent(intent)
		loginViewModel.parseIntentData(intent)
	}
}
