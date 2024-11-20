package com.farukaygun.yorozuyalist.presentation

import com.farukaygun.yorozuyalist.util.enums.ScreenType

sealed class Screen(
	val route: String,
	val navArg: String = "",
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

	data object UserAnimeListScreen : Screen(
		route = "user_anime_list_screen",
		navArg = "/${ScreenType.ANIME}",
		isSearchBarVisible = true,
		isBottomNavBarVisible = true,
		title = "Anime List"
	)

	data object UserMangaListScreen : Screen(
		route = "user_manga_list_screen",
		navArg = "/${ScreenType.MANGA}",
		isSearchBarVisible = true,
		isBottomNavBarVisible = true,
		title = "Manga List"
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

	data object DetailScreen : Screen(
		route = "detail_screen",
		isSearchBarVisible = false,
		isBottomNavBarVisible = false,
		title = "Detail"
	)

	fun getScreen(route: String?): Screen? {
		return when {
			LoginScreen.route == route -> LoginScreen
			HomeScreen.route == route -> HomeScreen
			route?.startsWith(UserAnimeListScreen.route) == true -> UserAnimeListScreen
			route?.startsWith(UserMangaListScreen.route) == true -> UserMangaListScreen
			ProfileScreen.route == route -> ProfileScreen
			SearchScreen.route == route -> SearchScreen
			else -> null
		}
	}
}
