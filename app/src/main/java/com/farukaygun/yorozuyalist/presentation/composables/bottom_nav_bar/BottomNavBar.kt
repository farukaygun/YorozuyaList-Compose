package com.farukaygun.yorozuyalist.presentation.composables.bottom_nav_bar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomNavBar(
	navController: NavController
) {
	var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

	val items = listOf(
		BottomNavItem.Home,
		BottomNavItem.UserAnimeList,
		BottomNavItem.UserMangaList,
		BottomNavItem.Profile
	)

	NavigationBar {
		items.forEachIndexed { index, item ->
			NavigationBarItem(
				selected = selectedIndex == index,
				onClick = {
					selectedIndex = index
					navController.navigate(item.route)
				},
				icon = {
					Icon(
						painter = painterResource(
							id = if (selectedIndex == index) {
								item.selectedIcon
							} else {
								item.unselectedIcon
							}
						),
						contentDescription = item.label
					)
				},
				label = {
					Text(
						text = item.label,
					)
				}
			)
		}
	}
}

@Composable
@Preview
fun BottomNavBarPreview() {
	BottomNavBar(rememberNavController())
}