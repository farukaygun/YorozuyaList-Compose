package com.farukaygun.yorozuyalist.presentation.composables.bottom_nav_bar

import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.presentation.Screen

sealed class BottomNavItem(
	val screen: Screen,
	val unselectedIcon: Int,
	val selectedIcon: Int,
) {
	data object Home : BottomNavItem(
		screen = Screen.HomeScreen,
		unselectedIcon = R.drawable.home_unfilled_24px,
		selectedIcon = R.drawable.home_24px,
	)

	data object UserAnimeList : BottomNavItem(
		screen = Screen.UserAnimeListScreen,
		unselectedIcon = R.drawable.movie_unfilled_24px,
		selectedIcon = R.drawable.movie_24px,
	)

	data object UserMangaList : BottomNavItem(
		screen = Screen.UserMangaListScreen,
		unselectedIcon = R.drawable.book_unfilled_24px,
		selectedIcon = R.drawable.book_24px,
	)

	data object Profile : BottomNavItem(
		screen = Screen.ProfileScreen,
		unselectedIcon = R.drawable.person_unfilled_24px,
		selectedIcon = R.drawable.person_24px,
	)
}