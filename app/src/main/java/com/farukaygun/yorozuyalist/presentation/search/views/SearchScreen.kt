package com.farukaygun.yorozuyalist.presentation.search.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
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

@Composable
fun SearchScreen(
	navController: NavController,
	viewModel: SearchViewModel = koinViewModel()
) {
	val state = viewModel.state.value

	Column(
		modifier = Modifier.statusBarsPadding()
	) {
		SearchBar(
			navController = navController,
			viewModel = viewModel
		)

		if (!state.isLoading && state.animeSearched?.data?.isNotEmpty() == true) {
			SearchList(
				navController = navController,
				data = state.animeSearched.data,
				viewModel = viewModel
			)
		} else if (state.isLoading) {
			ShimmerEffectVerticalList()
		}
	}

	if (state.error.isNotEmpty()) {
		Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_SHORT).show()
	}
}

@Composable
fun SearchBar(
	navController: NavController,
	viewModel: SearchViewModel
) {
	var query by rememberSaveable(stateSaver = TextFieldValue.Saver) {
		mutableStateOf(TextFieldValue())
	}
	val focusRequester = remember { FocusRequester() }
	val keyboardController = LocalSoftwareKeyboardController.current

	Column(
		modifier = Modifier
			.verticalScroll(rememberScrollState())
	) {
		TextField(
			value = query,
			onValueChange = {
				query = it
				// if at least 3 non whitespace characters are entered, search
				if (query.text.replace("\\s".toRegex(), "").length >= 3)
					viewModel.onEvent(SearchEvent.Search(query.text))
			},
			modifier = Modifier
				.fillMaxWidth()
				.focusRequester(focusRequester),
			placeholder = {
				Text(
					text = "Search",
					modifier = Modifier
						.weight(1f)
						.alpha(.5f),
					color = MaterialTheme.colorScheme.onBackground,
					style = MaterialTheme.typography.bodyMedium,
				)
			},
			leadingIcon = {
				IconButton(onClick = { navController.popBackStack() }) {
					Icon(
						painter = painterResource(id = R.drawable.arrow_back_24px),
						contentDescription = "Search",
						tint = MaterialTheme.colorScheme.onBackground,
					)
				}
			},
			keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
			keyboardActions = KeyboardActions(onSearch = {
				keyboardController?.hide()
			}),
			singleLine = true,
			colors = TextFieldDefaults.colors(
				focusedContainerColor = Color.Transparent,
				unfocusedContainerColor = Color.Transparent,
				focusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
				unfocusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant
			),
		)
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
		listState.scrollToItem(0)
		viewModel.scrollToTop.value = false
	}


	LazyColumn(
		state = listState,
		modifier = Modifier.padding(16.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		items(data) { anime ->
			ListItemColumn(
				data = anime,
				onItemClick = { navController.navigate(Screen.DetailScreen.route + "/${ScreenType.ANIME.name}/${anime.node.id}") }
			)
		}

		if (viewModel.state.value.isLoadingMore) {
			item {
				Column(
					modifier = Modifier.padding(16.dp),
					verticalArrangement = Arrangement.Center,
					horizontalAlignment = Alignment.CenterHorizontally
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