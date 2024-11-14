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
import com.farukaygun.yorozuyalist.presentation.Screen.UserAnimeListScreen.getScreen

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
			bottomNavBarState.items.forEachIndexed { _, bottomNavItem ->
				NavigationBarItem(
					selected = bottomNavBarState.currentScreen?.route == bottomNavItem.screen.route,
					onClick = {
						val route = bottomNavItem.screen.route + bottomNavItem.screen.navArg
						navController.navigate(route) {
							navController.graph.startDestinationRoute?.let {
								popUpTo(it) {
									saveState = true
								}
								launchSingleTop = true
								restoreState = true
							}
						}
						bottomNavBarState.currentScreen = getScreen(bottomNavItem.screen.route)
					},
					icon = {
						Icon(
							painter = painterResource(
								id = if (bottomNavItem.screen.route == bottomNavBarState.currentScreen?.route) {
									bottomNavItem.selectedIcon
								} else {
									bottomNavItem.unselectedIcon
								}
							),
							contentDescription = bottomNavItem.screen.title
						)
					},
					label = {
						Text(
							text = bottomNavItem.screen.title,
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