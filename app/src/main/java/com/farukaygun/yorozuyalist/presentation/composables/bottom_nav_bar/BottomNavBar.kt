package com.farukaygun.yorozuyalist.presentation.composables.bottom_nav_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.farukaygun.yorozuyalist.presentation.Screen.UserListScreen.getScreen

@Composable
fun BottomNavBar(
	navController: NavController,
	bottomNavBarState: BottomNavBarState
) {
	AnimatedVisibility(
		visible = bottomNavBarState.isVisible,
		enter = expandVertically(),
		exit = shrinkVertically()
	) {
		NavigationBar {
			bottomNavBarState.items.forEachIndexed { index, item ->
				NavigationBarItem(
					selected = bottomNavBarState.currentScreen?.route == item.route,
					onClick = {
						navController.navigate(item.route)
						bottomNavBarState.currentScreen = getScreen(item.route)
					},
					icon = {
						Icon(
							painter = painterResource(
								id = if (bottomNavBarState.currentScreen?.route == item.route) {
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
}

@Composable
@Preview
fun BottomNavBarPreview() {
	val navController = rememberNavController()
	val scope = rememberCoroutineScope()

	BottomNavBar(
		navController = navController,
		bottomNavBarState = BottomNavBarState(navController, scope)
	)
}