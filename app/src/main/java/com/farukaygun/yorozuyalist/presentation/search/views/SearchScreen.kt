package com.farukaygun.yorozuyalist.presentation.search.views

import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = koinViewModel()
) {
    val state = viewModel.state.value
    var query by rememberSaveable { mutableStateOf("") }
    var isActive by rememberSaveable { mutableStateOf(false) }
    val horizontalPadding by animateDpAsState(
        targetValue = if (isActive) 0.dp else 16.dp,
        label = "horizontalPadding"
    )

    Box(
        modifier = Modifier
            .statusBarsPadding()
    ) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = { newQuery ->
                        query = newQuery
                        if (newQuery.replace("\\s".toRegex(), "").length >= 3) {
                            viewModel.onEvent(SearchEvent.Search(newQuery))
                        }
                    },
                    onSearch = {
                        isActive = false
                    },
                    expanded = isActive,
                    onExpandedChange = { isActive = it },
                    placeholder = { Text("Search") },
                    leadingIcon = {
                        IconButton(onClick = {
                            if (isActive) {
                                isActive = false
                            } else {
                                navController.popBackStack()
                            }}) {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_back_24px),
                                contentDescription = "Back"
                            )
                        }
                    },
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = {
                                query = ""
                                viewModel.onEvent(SearchEvent.Search(""))
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.close_24px),
                                    contentDescription = "Clear"
                                )
                            }
                        } else {
                            Icon(
                                painter = painterResource(id =  R.drawable.search_24px),
                                contentDescription = "Search"
                            )
                        }
                    }
                )
            },
            expanded = isActive,
            onExpandedChange = { isActive = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding)
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
                !state.isLoading && state.animeSearched?.data?.isNotEmpty() == true -> {
                    SearchList(
                        navController = navController,
                        data = state.animeSearched.data,
                        viewModel = viewModel
                    )
                }
                query.isNotEmpty() && !state.isLoading && state.animeSearched?.data?.isEmpty() == true -> {
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
    }

    if (state.error.isNotEmpty()) {
        Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun SearchList(
    navController: NavController,
    data: List<Data>,
    viewModel: SearchViewModel
) {
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

        if (viewModel.state.value.isLoadingMore) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}



@Composable
@Preview
fun SearchScreenPreview() {
	val context = LocalContext.current

	KoinApplication(application = {
		androidContext(context)
		modules(
			viewModelModule, repositoryModule, useCaseModule, apiServiceModule
		)
	}) {
		SearchScreen(
			navController = rememberNavController()
		)
	}
}