package com.farukaygun.yorozuyalist.presentation.composables.bottom_nav_bar

import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.presentation.Screen
import com.farukaygun.yorozuyalist.util.ListType

sealed class BottomNavItem(
	val route: String,
	val unselectedIcon: Int,
	val selectedIcon: Int,
	val label: String,
) {
	data object Home : BottomNavItem(
		route = Screen.HomeScreen.route,
		unselectedIcon = R.drawable.outline_home_24px,
		selectedIcon = R.drawable.filled_outline_home_24px,
		label = Screen.HomeScreen.title
	)

	data object UserAnimeList : BottomNavItem(
		route = Screen.UserListScreen.route + "/${ListType.ANIME_LIST.name}",
		unselectedIcon = R.drawable.outline_movie_24px,
		selectedIcon = R.drawable.filled_outline_movie_24px,
		label = Screen.UserListScreen.title
	)

	data object UserMangaList : BottomNavItem(
		route = Screen.UserListScreen.route + "/${ListType.MANGA_LIST.name}",
		unselectedIcon = R.drawable.outline_book_24px,
		selectedIcon = R.drawable.filled_outline_book_24px,
		label = Screen.UserListScreen.title
	)

	data object Profile : BottomNavItem(
		route = Screen.ProfileScreen.route,
		unselectedIcon = R.drawable.outline_person_24px,
		selectedIcon = R.drawable.filled_outline_person_24px,
		label = Screen.ProfileScreen.title
	)
}