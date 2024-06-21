package com.farukaygun.yorozuyalist.presentation

sealed class Screen(
	val route: String,
	val isAppBarVisible: Boolean = false,
	val title: String = ""
) {
	data object LoginScreen : Screen(
		route = "login_screen",
		title = "Login"
	)
	data object HomeScreen : Screen(
		route = "home_screen",
		isAppBarVisible = true,
		title = "Home"
	)

	data object SearchScreen : Screen(
		route = "search_screen",
		isAppBarVisible = false,
		title = "Search"
	)

	fun getScreen(route: String?): Screen? = when (route) {
		"home_screen" -> HomeScreen
		"login_screen" -> LoginScreen
		"search_screen" -> SearchScreen
		else -> null
	}
}
