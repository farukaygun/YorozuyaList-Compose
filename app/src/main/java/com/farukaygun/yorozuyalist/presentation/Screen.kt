package com.farukaygun.yorozuyalist.presentation

sealed class Screen(
	val route: String,
	val isSearchBarVisible: Boolean = false,
	val isBottomNavBarVisible: Boolean = false,
	val title: String = ""
) {
	data object LoginScreen : Screen(
		route = "login_screen",
		isSearchBarVisible = false,
		isBottomNavBarVisible = false,
		title = "Login"
	)

	data object HomeScreen : Screen(
		route = "home_screen",
		isSearchBarVisible = true,
		isBottomNavBarVisible = true,
		title = "Home"
	)

	data object SearchScreen : Screen(
		route = "search_screen",
		isSearchBarVisible = false,
		isBottomNavBarVisible = true,
		title = "Search"
	)

	data object UserListScreen : Screen(
		route = "user_list_screen",
		isSearchBarVisible = true,
		isBottomNavBarVisible = true,
		title = "User List"
	)

	data object ProfileScreen : Screen(
		route = "profile_screen",
		isSearchBarVisible = false,
		isBottomNavBarVisible = true,
		title = "Profile"
	)

	data object GridListScreen : Screen(
		route = "grid_list_screen",
		isSearchBarVisible = false,
		isBottomNavBarVisible = true,
		title = "Grid List"

	)

	fun getScreen(route: String?): Screen? = when {
		LoginScreen.route == route -> LoginScreen
		HomeScreen.route == route -> HomeScreen
		UserListScreen.route == route || route?.startsWith(UserListScreen.route) == true -> UserListScreen
		ProfileScreen.route == route -> ProfileScreen
		SearchScreen.route == route -> SearchScreen
		else -> null
	}
}
