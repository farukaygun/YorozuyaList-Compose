package com.farukaygun.yorozuyalist.presentation.search.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.data.di.apiServiceModule
import com.farukaygun.yorozuyalist.data.di.repositoryModule
import com.farukaygun.yorozuyalist.data.di.useCaseModule
import com.farukaygun.yorozuyalist.data.di.viewModelModule
import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.presentation.Screen
import com.farukaygun.yorozuyalist.presentation.composables.ListItemColumn
import com.farukaygun.yorozuyalist.presentation.composables.OnBottomReached
import com.farukaygun.yorozuyalist.presentation.composables.shimmer_effect.ShimmerEffectVerticalList
import com.farukaygun.yorozuyalist.presentation.search.SearchEvent
import com.farukaygun.yorozuyalist.presentation.search.SearchViewModel
import com.farukaygun.yorozuyalist.util.enums.ScreenType
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SearchViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val animeSearched = state.animeSearched
    val searchBarState = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        snapshotFlow { textFieldState.text.toString() }
            .distinctUntilChanged()
            .collect { query ->
                if (query.replace("\\s".toRegex(), "").length >= 3) {
                    viewModel.onEvent(SearchEvent.Search(query))
                }
            }
    }

    SearchBar(
        state = searchBarState,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        inputField = {
            SearchBarDefaults.InputField(
                textFieldState = textFieldState,
                searchBarState = searchBarState,
                onSearch = {
                    coroutineScope.launch { searchBarState.animateToCollapsed() }
                },
                placeholder = { Text("Search") },
                leadingIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back_24px),
                            contentDescription = "Back"
                        )
                    }
                },
                trailingIcon = {
                    if (textFieldState.text.isNotEmpty()) {
                        IconButton(onClick = {
                            textFieldState.edit { replace(0, originalText.length, "") }
                            viewModel.onEvent(SearchEvent.Search(""))
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.close_24px),
                                contentDescription = "Clear"
                            )
                        }
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.search_24px),
                            contentDescription = "Search"
                        )
                    }
                }
            )
        }
    )

    ExpandedFullScreenSearchBar(
        state = searchBarState,
        inputField = {
            SearchBarDefaults.InputField(
                textFieldState = textFieldState,
                searchBarState = searchBarState,
                onSearch = {
                    coroutineScope.launch { searchBarState.animateToCollapsed() }
                },
                placeholder = { Text("Search") },
                leadingIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch { searchBarState.animateToCollapsed() }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back_24px),
                            contentDescription = "Back"
                        )
                    }
                },
                trailingIcon = {
                    if (textFieldState.text.isNotEmpty()) {
                        IconButton(onClick = {
                            textFieldState.edit { replace(0, originalText.length, "") }
                            viewModel.onEvent(SearchEvent.Search(""))
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.close_24px),
                                contentDescription = "Clear"
                            )
                        }
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.search_24px),
                            contentDescription = "Search"
                        )
                    }
                }
            )
        }
    ) {
        when {
            state.isLoading -> {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    ShimmerEffectVerticalList()
                }
            }
            !state.isLoading && animeSearched?.data?.isNotEmpty() == true -> {
                SearchList(
                    navController = navController,
                    data = animeSearched.data,
                    viewModel = viewModel
                )
            }
            textFieldState.text.isNotEmpty() && !state.isLoading && state.animeSearched?.data?.isEmpty() == true -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No results found",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }

    state.error?.let { error ->
        Toast.makeText(LocalContext.current, error.toMessage(), Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SearchList(
    navController: NavController,
    data: List<Data>,
    viewModel: SearchViewModel
) {
    val searchState by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    listState.OnBottomReached(buffer = 10) {
        viewModel.onEvent(SearchEvent.LoadMore)
    }

    LaunchedEffect(viewModel.scrollToTop.value) {
        if (viewModel.scrollToTop.value) {
            listState.scrollToItem(0)
            viewModel.scrollToTop.value = false
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(data) { anime ->
            ListItemColumn(
                data = anime,
                onItemClick = {
                    navController.navigate(
                        Screen.DetailScreen.route + "/${ScreenType.ANIME.name}/${anime.node.id}"
                    )
                }
            )
        }

        if (searchState.isLoadingMore) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularWavyProgressIndicator()
                }
            }
        }
    }
}

@Composable
@Preview
fun SearchScreenPreview() {
    val context = LocalContext.current

    KoinApplication(configuration = koinConfiguration(declaration = {
        androidContext(context)
        modules(
            viewModelModule, repositoryModule, useCaseModule, apiServiceModule
        )
    }), content = {
        SearchScreen(
            navController = rememberNavController()
        )
    })
}