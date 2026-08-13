package com.farukaygun.yorozuyalist.presentation.composables.bottom_nav_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.farukaygun.yorozuyalist.presentation.Screen.UserAnimeListScreen.getScreen

object BottomNavBarDefaults {
	/**
	 * Height of [BottomNavBar]'s HorizontalFloatingToolbar, excluding the navigation
	 * bar inset: icon (24.dp) + item vertical padding (8.dp * 2) + the toolbar's own
	 * container padding. Keep in sync with the item padding in [BottomNavBar] — if the
	 * labels are ever re-enabled, re-measure this with the Layout Inspector.
	 */
	val ContainerHeight = 64.dp

	/** Breathing room so content doesn't sit flush against the toolbar edge. */
	val ContentGap = 16.dp

	/**
	 * Vertical space a scrollable screen must reserve at its bottom so its last item can
	 * be scrolled clear of the floating toolbar, which overlays the content.
	 *
	 * Reserve it *inside* the scroll container — `contentPadding` on a lazy list, or a
	 * `padding` modifier applied *after* `verticalScroll`. Applied as an outer inset it
	 * shrinks the viewport instead, and content stops above the bar rather than scrolling
	 * under it.
	 *
	 * Deliberately independent of the Scaffold's own `calculateBottomPadding()`: that
	 * value animates with the bar's AnimatedVisibility (and snaps to 0 on dispose), which
	 * would make content shift every time the bar hides or shows.
	 *
	 * Only meaningful for screens whose [com.farukaygun.yorozuyalist.presentation.Screen]
	 * has `isBottomAppBarVisible = true`.
	 */
	@Composable
	fun contentBottomPadding(): Dp =
		ContainerHeight + ContentGap +
				WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BottomNavBar(
	navController: NavController,
	bottomAppBarState: BottomAppBarState,
) {
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.navigationBarsPadding(),
		contentAlignment = Alignment.Center
	) {
		HorizontalFloatingToolbar(
			expanded = true,
			modifier = Modifier.wrapContentWidth(),
		) {
			bottomAppBarState.items.forEach { item ->
				val isSelected = bottomAppBarState.currentScreen?.route == item.screen.route
				val contentColor = if (isSelected)
					MaterialTheme.colorScheme.primary
				else
					MaterialTheme.colorScheme.onSurfaceVariant

				Column(
					modifier = Modifier
						.wrapContentWidth()
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
						}
						.padding(horizontal = 16.dp, vertical = 8.dp),
					horizontalAlignment = Alignment.CenterHorizontally,
				) {
					Icon(
						painter = painterResource(
							id = if (isSelected) item.selectedIcon else item.unselectedIcon
						),
						contentDescription = item.screen.title,
						tint = contentColor
					)
//					if (isSelected) {
//						Text(
//							text = item.screen.title,
//							style = MaterialTheme.typography.labelSmall,
//							color = contentColor
//						)
//					}
				}
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
