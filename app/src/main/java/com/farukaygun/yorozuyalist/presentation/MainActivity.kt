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
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.farukaygun.yorozuyalist.presentation.calendar.views.CalendarScreen
import com.farukaygun.yorozuyalist.presentation.composables.bottom_nav_bar.BottomNavBar
import com.farukaygun.yorozuyalist.presentation.composables.bottom_nav_bar.rememberBottomAppBarState
import com.farukaygun.yorozuyalist.presentation.composables.search_bar.rememberSearchBarState
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

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                val navController = rememberNavController()
                val searchBarState = rememberSearchBarState(navController = navController)
                val bottomAppBarState = rememberBottomAppBarState(navController = navController)
                var currentListState by remember { mutableStateOf<LazyListState?>(null) }
                var isScaffoldBarVisible by remember { mutableStateOf(true) }
                var scrollState by remember { mutableStateOf(ScrollState.IDLE) }
                var accumulatedScroll = 0f
                val threshold = 5f

                val nestedScrollConnection = remember {
                    object : NestedScrollConnection {
                        override fun onPreScroll(
                            available: Offset,
                            source: NestedScrollSource
                        ): Offset {
                            currentListState?.takeIf {
                                it.firstVisibleItemIndex == 0 && it.firstVisibleItemScrollOffset == 0
                            }?.let {
                                isScaffoldBarVisible = true
                                return Offset.Zero
                            }

                            currentListState?.takeIf {
                                it.layoutInfo.visibleItemsInfo.lastOrNull()?.index == it.layoutInfo.totalItemsCount - 1
                            }?.let {
                                return Offset.Zero
                            }

                            val currentScrollDirection = when {
                                available.y < -1f -> ScrollState.SCROLLING_DOWN
                                available.y > 1f -> ScrollState.SCROLLING_UP
                                else -> ScrollState.IDLE
                            }

                            if (scrollState != currentScrollDirection && currentScrollDirection != ScrollState.IDLE) {
                                accumulatedScroll = 0f
                                scrollState = currentScrollDirection
                            }

                            if (currentScrollDirection != ScrollState.IDLE) {
                                accumulatedScroll += abs(available.y)
                            }

                            return when (scrollState) {
                                ScrollState.SCROLLING_DOWN if isScaffoldBarVisible &&
                                        accumulatedScroll >= threshold -> {
                                    isScaffoldBarVisible = false
                                    available
                                }

                                ScrollState.SCROLLING_UP if !isScaffoldBarVisible &&
                                        accumulatedScroll >= threshold -> {
                                    isScaffoldBarVisible = true
                                    available
                                }
                                else -> Offset.Zero
                            }
                        }
                    }
                }

                navController.addOnDestinationChangedListener { _, _, _ ->
                    isScaffoldBarVisible = true
                    accumulatedScroll = 0f
                }

                Scaffold(topBar = {
                    AnimatedVisibility(
                        modifier = Modifier.systemBarsPadding(),
                        visible = searchBarState.isVisible && isScaffoldBarVisible,
                        enter = expandVertically(
                            expandFrom = Alignment.Bottom,
                            animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                        ),
                        exit = shrinkVertically(
                            shrinkTowards = Alignment.Bottom,
                            animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                        )
                    ) {
                        SearchScreen(navController = navController)
                    }

                    if (!searchBarState.isVisible || !isScaffoldBarVisible) {
                        Spacer(modifier = Modifier.systemBarsPadding())
                    }
                }, bottomBar = {
                    AnimatedVisibility(
                        visible = bottomAppBarState.isEnabled && isScaffoldBarVisible,
                        enter = expandVertically(
                            expandFrom = Alignment.Bottom,
                            animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                        ),
                        exit = shrinkVertically(
                            shrinkTowards = Alignment.Bottom,
                            animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                        )
                    ) {
                        BottomNavBar(
                            navController = navController,
                            bottomAppBarState = bottomAppBarState
                        )
                    }
                }) { paddingValues ->
                    val targetTopPadding = paddingValues.calculateTopPadding()
                    val targetBottomPadding = paddingValues.calculateBottomPadding()

                    val animatedTopPadding by animateDpAsState(
                        targetValue = targetTopPadding,
                        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                        label = "TopPaddingAnimation"
                    )

                    val animatedBottomPadding by animateDpAsState(
                        targetValue = targetBottomPadding,
                        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                        label = "BottomPaddingAnimation"
                    )

                    NavHost(
                        modifier = Modifier.padding(
                            top = animatedTopPadding,
                            bottom = animatedBottomPadding
                        ),
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
                                onListStateChanged = { listState ->
                                    currentListState = listState
                                }
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
                                onListStateChanged = { listState ->
                                    currentListState = listState
                                }
                            )
                        }

                        composable(
                            route = Screen.ProfileScreen.route,
                            enterTransition = { fadeIn() },
                            exitTransition = { fadeOut() },
                            popEnterTransition = { fadeIn() },
                            popExitTransition = { fadeOut() },
                        ) {
                            ProfileScreen()
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
                                nestedScrollConnection = nestedScrollConnection,
                                onListStateChanged = { listState ->
                                    currentListState = listState
                                }
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
