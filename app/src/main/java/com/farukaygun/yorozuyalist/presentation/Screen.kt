package com.farukaygun.yorozuyalist.presentation

sealed class Screen(
	val route: String,
	val isAppBarVisible: Boolean = false,
	val title: String = ""
) {
	data object LoginScreen : Screen(
		route = "login_screen"
	)
	data object HomeScreen : Screen(
		route = "home_screen",
		isAppBarVisible = true,
		title = "Home"
	)

	fun getScreen(route: String?): Screen? = when (route) {
		"home_screen" -> HomeScreen
		"login_screen" -> LoginScreen
		else -> null
	}
}
