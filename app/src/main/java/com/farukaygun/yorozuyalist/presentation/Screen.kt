package com.farukaygun.yorozuyalist.presentation

sealed class Screen(val route: String) {
	data object LoginScreen : Screen("login_screen")
	data object HomeScreen : Screen("home_screen")
}
