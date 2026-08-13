package com.farukaygun.yorozuyalist.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.farukaygun.yorozuyalist.presentation.calendar.views.CalendarScreen
import com.farukaygun.yorozuyalist.presentation.composables.bottom_nav_bar.BottomNavBar
import com.farukaygun.yorozuyalist.presentation.composables.bottom_nav_bar.rememberBottomAppBarState
import com.farukaygun.yorozuyalist.presentation.detail.views.DetailScreen
import com.farukaygun.yorozuyalist.presentation.grid_list.views.GridListScreen
import com.farukaygun.yorozuyalist.presentation.home.views.HomeScreen
import com.farukaygun.yorozuyalist.presentation.login.LoginEvent
import com.farukaygun.yorozuyalist.presentation.login.LoginViewModel
import com.farukaygun.yorozuyalist.presentation.login.views.LoginScreen
import com.farukaygun.yorozuyalist.presentation.profile.views.ProfileScreen
import com.farukaygun.yorozuyalist.presentation.search.views.SearchScreen
import com.farukaygun.yorozuyalist.presentation.user_list.views.UserListScreen
import com.farukaygun.yorozuyalist.ui.theme.AppTheme
import com.farukaygun.yorozuyalist.util.enums.ScrollState
import org.koin.android.ext.android.inject
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by inject()

    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                val navController = rememberNavController()
                val bottomAppBarState = rememberBottomAppBarState(navController = navController)
                val barVisibleState = remember { mutableStateOf(true) }
                val isScaffoldBarVisible by barVisibleState

                val nestedScrollConnection = remember {
                    BarVisibilityNestedScrollConnection(barVisibleState)
                }

                DisposableEffect(navController) {
                    val listener =
                        NavController.OnDestinationChangedListener { _, _, _ ->
                            barVisibleState.value = true
                            nestedScrollConnection.reset()
                        }
                    navController.addOnDestinationChangedListener(listener)
                    onDispose { navController.removeOnDestinationChangedListener(listener) }
                }

                Scaffold(
                    contentWindowInsets = WindowInsets(0),
                    bottomBar = {
                        AnimatedVisibility(
                            visible = bottomAppBarState.isEnabled && isScaffoldBarVisible,
                            enter = slideInVertically(
                                initialOffsetY = { it },
                                animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                            ) + expandVertically(
                                expandFrom = Alignment.Bottom,
                                animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                            ) + fadeIn(animationSpec = spring(stiffness = Spring.StiffnessMediumLow)),
                            exit = scaleOut(
                                targetScale = 0f,
                                transformOrigin = androidx.compose.ui.graphics.TransformOrigin(0.5f, 1f),
                                animationSpec = spring(stiffness = Spring.StiffnessLow)
                            ) + slideOutVertically(
                                targetOffsetY = { it },
                                animationSpec = spring(stiffness = Spring.StiffnessLow)
                            )
                        ) {
                            BottomNavBar(
                                navController = navController,
                                bottomAppBarState = bottomAppBarState
                            )
                        }
                    }
                ) { paddingValues ->
                    NavHost(
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(top = paddingValues.calculateTopPadding()),
                        navController = navController,
                        startDestination = if (loginViewModel.isLoggedIn()) Screen.HomeScreen.route else Screen.LoginScreen.route,
                        enterTransition = {
                            fadeIn() + slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                                animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                            )
                        },
                        exitTransition = {
                            fadeOut() + slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.End,
                                animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                            )
                        },
                        popEnterTransition = {
                            fadeIn()
                        },
                        popExitTransition = {
                            fadeOut() + slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.End,
                                animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                            )
                        }) {
                        composable(
                            route = Screen.LoginScreen.route,
                        ) {
                            LoginScreen(
                                navController = navController, viewModel = loginViewModel
                            )
                        }

                        composable(
                            route = Screen.HomeScreen.route,
                            enterTransition = { fadeIn() },
                            exitTransition = { fadeOut() },
                            popEnterTransition = { fadeIn() },
                            popExitTransition = { fadeOut() },
                        ) {
                            BackHandler(true) {
                                Log.d("MainActivity", "Navigation Home: Back Pressed")
                            }

                            HomeScreen(
                                navController = navController,
                                nestedScrollConnection = nestedScrollConnection,
                                isTopBarVisible = isScaffoldBarVisible
                            )
                        }

                        composable(
                            route = "${Screen.UserAnimeListScreen.route}/{SCREEN_TYPE_PARAM}",
                            enterTransition = { fadeIn() },
                            exitTransition = { fadeOut() },
                            popEnterTransition = { fadeIn() },
                            popExitTransition = { fadeOut() },
                            arguments = listOf(
                                navArgument("SCREEN_TYPE_PARAM") {
                                    type = NavType.StringType
                                })
                        ) {
                            UserListScreen(
                                navController = navController,
                                nestedScrollConnection = nestedScrollConnection,
                                isTopBarVisible = isScaffoldBarVisible
                            )
                        }

                        composable(
                            route = Screen.UserMangaListScreen.route + "/{SCREEN_TYPE_PARAM}",
                            enterTransition = { fadeIn() },
                            exitTransition = { fadeOut() },
                            popEnterTransition = { fadeIn() },
                            popExitTransition = { fadeOut() },
                            arguments = listOf(
                                navArgument("SCREEN_TYPE_PARAM") {
                                    type = NavType.StringType
                                })
                        ) {
                            UserListScreen(
                                navController = navController,
                                nestedScrollConnection = nestedScrollConnection,
                                isTopBarVisible = isScaffoldBarVisible
                            )
                        }

                        composable(
                            route = Screen.ProfileScreen.route,
                            enterTransition = { fadeIn() },
                            exitTransition = { fadeOut() },
                            popEnterTransition = { fadeIn() },
                            popExitTransition = { fadeOut() },
                        ) {
                            ProfileScreen(nestedScrollConnection = nestedScrollConnection)
                        }

                        composable(
                            route = Screen.SearchScreen.route
                        ) {
                            SearchScreen(navController = navController)
                        }

                        composable(
                            route = Screen.GridListScreen.route + "/{SCREEN_TYPE_PARAM}",
                            arguments = listOf(
                                navArgument("SCREEN_TYPE_PARAM") {
                                    type = NavType.StringType
                                })
                        ) {
                            val type = it.arguments?.getString("SCREEN_TYPE_PARAM") ?: ""
                            GridListScreen(
                                navController = navController,
                                type = type
                            )
                        }

                        composable(
                            route = Screen.DetailScreen.route + "/{SCREEN_TYPE_PARAM}/{MEDIA_ID_PARAM}",
                            arguments = listOf(navArgument("SCREEN_TYPE_PARAM") {
                                type = NavType.StringType
                            }, navArgument("MEDIA_ID_PARAM") {
                                type = NavType.StringType
                            })
                        ) {
                            val type = it.arguments?.getString("SCREEN_TYPE_PARAM") ?: ""
                            DetailScreen(
                                navController = navController, type = type
                            )
                        }

                        composable(
                            route = Screen.CalendarScreen.route,
                            enterTransition = { fadeIn() },
                            exitTransition = { fadeOut() },
                            popEnterTransition = { fadeIn() },
                            popExitTransition = { fadeOut() }
                        ) {
                            CalendarScreen(
                                navController = navController,
                                nestedScrollConnection = nestedScrollConnection
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        loginViewModel.onEvent(LoginEvent.ParseIntentData(applicationContext, intent))
    }
}

/**
 * Hides the search bar and the bottom navigation bar while scrolling down, and reveals
 * them again on scroll up or at the top of the content.
 *
 * Deliberately free of any [androidx.compose.foundation.lazy.LazyListState]: it reads
 * nothing but the scroll deltas, so it works with `LazyColumn` and plain `verticalScroll`
 * screens alike (Home and Profile use the latter).
 *
 * [accumulatedScroll] and [scrollDirection] are plain fields rather than Compose state on
 * purpose — they are only touched inside the scroll callbacks and never read during
 * composition. As captured local `var`s they were silently broken: each composition boxed
 * them into a separate `Ref`, so the reset on navigation wrote to a box this connection
 * never read.
 */
private class BarVisibilityNestedScrollConnection(
    private val isBarVisible: MutableState<Boolean>,
    private val threshold: Float = 5f
) : NestedScrollConnection {
    private var accumulatedScroll = 0f
    private var scrollDirection = ScrollState.IDLE

    fun reset() {
        accumulatedScroll = 0f
        scrollDirection = ScrollState.IDLE
    }

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        val direction = when {
            available.y < -1f -> ScrollState.SCROLLING_DOWN
            available.y > 1f -> ScrollState.SCROLLING_UP
            else -> ScrollState.IDLE
        }

        if (direction == ScrollState.IDLE) return Offset.Zero

        if (scrollDirection != direction) {
            accumulatedScroll = 0f
            scrollDirection = direction
        }
        accumulatedScroll += abs(available.y)

        if (accumulatedScroll >= threshold) {
            isBarVisible.value = direction == ScrollState.SCROLLING_UP
        }

        // Never consume: the scrollable itself still needs the full delta.
        return Offset.Zero
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        // A downward drag the content did not consume means we are already at the top,
        // so the bars should always be showing there.
        if (available.y > 0f) {
            isBarVisible.value = true
            reset()
        }
        return Offset.Zero
    }
}
