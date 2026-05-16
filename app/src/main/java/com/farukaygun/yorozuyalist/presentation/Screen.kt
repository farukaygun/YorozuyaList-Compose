package com.farukaygun.yorozuyalist.presentation

import com.farukaygun.yorozuyalist.util.enums.ScreenType

sealed class Screen(
	val route: String,
	val navArg: String = "",
	val title: String = "",
	val isBottomAppBarVisible: Boolean = false,
) {
	data object LoginScreen : Screen(
		route = "login_screen",
		title = "Login",
		isBottomAppBarVisible = false
	)

	data object HomeScreen : Screen(
		route = "home_screen",
		title = "Home",
		isBottomAppBarVisible = true
	)

	data object SearchScreen : Screen(
		route = "search_screen",
		title = "Search",
		isBottomAppBarVisible = false
	)

	data object UserAnimeListScreen : Screen(
		route = "user_anime_list_screen",
		navArg = "/${ScreenType.ANIME}",
		title = "Anime List",
		isBottomAppBarVisible = true
	)

	data object UserMangaListScreen : Screen(
		route = "user_manga_list_screen",
		navArg = "/${ScreenType.MANGA}",
		title = "Manga List",
		isBottomAppBarVisible = true
	)

	data object ProfileScreen : Screen(
		route = "profile_screen",
		title = "Profile",
		isBottomAppBarVisible = true
	)

	data object GridListScreen : Screen(
		route = "grid_list_screen",
		title = "Grid List",
		isBottomAppBarVisible = false
	)

	data object DetailScreen : Screen(
		route = "detail_screen",
		title = "Detail",
		isBottomAppBarVisible = false
	)

	data object CalendarScreen : Screen(
		route = "calendar_screen",
		title = "Calendar",
		isBottomAppBarVisible = true
	)

	fun getScreen(route: String?): Screen? {
		return when {
			LoginScreen.route == route -> LoginScreen
			HomeScreen.route == route -> HomeScreen
			route?.startsWith(UserAnimeListScreen.route) == true -> UserAnimeListScreen
			route?.startsWith(UserMangaListScreen.route) == true -> UserMangaListScreen
			ProfileScreen.route == route -> ProfileScreen
			SearchScreen.route == route -> SearchScreen
			CalendarScreen.route == route -> CalendarScreen
			else -> null
		}
	}
}
