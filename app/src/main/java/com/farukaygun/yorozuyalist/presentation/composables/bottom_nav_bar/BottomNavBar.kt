package com.farukaygun.yorozuyalist.presentation.composables.bottom_nav_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.farukaygun.yorozuyalist.presentation.Screen.UserAnimeListScreen.getScreen

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BottomNavBar(
	navController: NavController,
	bottomAppBarState: BottomAppBarState,
) {
	HorizontalFloatingToolbar(
		expanded = true,
		shape = FloatingToolbarDefaults.ContainerShape,
		colors = FloatingToolbarDefaults.standardFloatingToolbarColors(),
		expandedShadowElevation = FloatingToolbarDefaults.ContainerExpandedElevation,
		collapsedShadowElevation = FloatingToolbarDefaults.ContainerCollapsedElevation,
		contentPadding = FloatingToolbarDefaults.ContentPadding,
		modifier = Modifier
			.fillMaxWidth()
			.navigationBarsPadding(),
	) {
		bottomAppBarState.items.forEach { item ->
			val isSelected = bottomAppBarState.currentScreen?.route == item.screen.route
			val contentColor = if (isSelected)
				MaterialTheme.colorScheme.primary
			else
				MaterialTheme.colorScheme.onSurfaceVariant

			Column(
				modifier = Modifier
					.weight(1f)
					.clip(FloatingToolbarDefaults.ContainerShape)
					.clickable {
						navController.navigate(item.screen.route + item.screen.navArg) {
							navController.graph.startDestinationRoute?.let {
								popUpTo(it) { saveState = true }
							}
							launchSingleTop = true
							restoreState = true
						}
						bottomAppBarState.currentScreen = getScreen(item.screen.route)
					},
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(4.dp)
			) {
				Icon(
					painter = painterResource(
						id = if (isSelected) item.selectedIcon else item.unselectedIcon
					),
					contentDescription = item.screen.title,
					tint = contentColor
				)
				Text(
					text = item.screen.title,
					style = MaterialTheme.typography.labelSmall,
					color = contentColor
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
		bottomAppBarState = BottomAppBarState(navController, scope)
	)
}
