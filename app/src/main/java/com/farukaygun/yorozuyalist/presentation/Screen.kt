package com.farukaygun.yorozuyalist.presentation

import com.farukaygun.yorozuyalist.util.enums.ScreenType

sealed class Screen(
	val route: String,
	val navArg: String = "",
	val title: String = "",
	val isSearchBarVisible: Boolean = false,
	val isBottomNavBarVisible: Boolean = false,
) {
	data object LoginScreen : Screen(
		route = "login_screen",
		title = "Login",
		isSearchBarVisible = false,
		isBottomNavBarVisible = false
	)

	data object HomeScreen : Screen(
		route = "home_screen",
		title = "Home",
		isSearchBarVisible = true,
		isBottomNavBarVisible = true
	)

	data object SearchScreen : Screen(
		route = "search_screen",
		title = "Search",
		isSearchBarVisible = false,
		isBottomNavBarVisible = false
	)

	data object UserAnimeListScreen : Screen(
		route = "user_anime_list_screen",
		navArg = "/${ScreenType.ANIME}",
		title = "Anime List",
		isSearchBarVisible = true,
		isBottomNavBarVisible = true
	)

	data object UserMangaListScreen : Screen(
		route = "user_manga_list_screen",
		navArg = "/${ScreenType.MANGA}",
		title = "Manga List",
		isSearchBarVisible = true,
		isBottomNavBarVisible = true
	)

	data object ProfileScreen : Screen(
		route = "profile_screen",
		title = "Profile",
		isSearchBarVisible = false,
		isBottomNavBarVisible = true
	)

	data object GridListScreen : Screen(
		route = "grid_list_screen",
		title = "Grid List",
		isSearchBarVisible = true,
		isBottomNavBarVisible = false
	)

	data object DetailScreen : Screen(
		route = "detail_screen",
		title = "Detail",
		isSearchBarVisible = false,
		isBottomNavBarVisible = false
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
