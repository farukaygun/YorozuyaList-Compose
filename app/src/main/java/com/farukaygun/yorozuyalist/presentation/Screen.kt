package com.farukaygun.yorozuyalist.presentation

sealed class Screen(
	val route: String,
	val isAppBarVisible: Boolean = false,
	val isBottomNavBarVisible: Boolean = false,
	val title: String = ""
) {
	data object LoginScreen : Screen(
		route = "login_screen",
		isAppBarVisible = false,
		isBottomNavBarVisible = false,
		title = "Login"
	)
	data object HomeScreen : Screen(
		route = "home_screen",
		isAppBarVisible = true,
		isBottomNavBarVisible = true,
		title = "Home"
	)

	data object SearchScreen : Screen(
		route = "search_screen",
		isAppBarVisible = false,
		isBottomNavBarVisible = true,
		title = "Search"
	)

	data object AnimeListScreen : Screen(
		route = "anime_list_screen",
		isAppBarVisible = true,
		isBottomNavBarVisible = true,
		title = "Anime List"
	)

	data object MangaListScreen : Screen(
		route = "manga_list_screen",
		isAppBarVisible = true,
		isBottomNavBarVisible = true,
		title = "Manga List"
	)

	data object ProfileScreen : Screen(
		route = "profile_screen",
		isAppBarVisible = false,
		isBottomNavBarVisible = true,
		title = "Profile"
	)

	fun getScreen(route: String?): Screen? = when (route) {
		"login_screen" -> LoginScreen
		"home_screen" -> HomeScreen
		"anime_list_screen" -> AnimeListScreen
		"manga_list_screen" -> MangaListScreen
		"profile_screen" -> ProfileScreen
		"search_screen" -> SearchScreen
		else -> null
	}
}
