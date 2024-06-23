package com.farukaygun.yorozuyalist.presentation.search.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
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
import com.farukaygun.yorozuyalist.domain.model.Data
import com.farukaygun.yorozuyalist.presentation.composables.views.OnBottomReached
import com.farukaygun.yorozuyalist.presentation.search.SearchEvent
import com.farukaygun.yorozuyalist.presentation.search.SearchState
import com.farukaygun.yorozuyalist.presentation.search.SearchViewModel
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
		modifier = Modifier
			.padding(bottom = 16.dp)
	) {
		SearchBar(
			navController = navController,
			viewModel = viewModel
		)

		state.animeSearched?.data?.let {
			SearchList(
				data = it,
				viewModel = viewModel
			)
		}
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
			.padding(top = 16.dp)
			.verticalScroll(rememberScrollState())
	) {
		Surface(
			modifier = Modifier
				.fillMaxWidth()
				.height(56.dp)
				.padding(horizontal = 16.dp),
			shape = RoundedCornerShape(32.dp),
			color = MaterialTheme.colorScheme.surfaceVariant,
		) {
			Row(
				verticalAlignment = Alignment.CenterVertically
			) {
				IconButton(
					onClick = { navController.popBackStack() }
				) {
					Icon(
						painter = painterResource(id = R.drawable.outline_arrow_back_24),
						contentDescription = "Search",
						tint = MaterialTheme.colorScheme.onSurfaceVariant,
					)
				}

				TextField(
					value = query,
					onValueChange = { query = it },
					modifier = Modifier
						.fillMaxWidth()
						.focusRequester(focusRequester),
					placeholder = { Text(
						text = "Search",
						modifier = Modifier
							.weight(1f)
							.alpha(.5f),
						color = MaterialTheme.colorScheme.onSurfaceVariant,
						style = MaterialTheme.typography.bodyMedium,
					) },
					keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
					keyboardActions = KeyboardActions(
						onSearch = {
							keyboardController?.hide()
							viewModel.onEvent(SearchEvent.Search(query.text))
						}
					),
					singleLine = true,
					colors = TextFieldDefaults.colors(
						focusedIndicatorColor = Color.Transparent,
						unfocusedIndicatorColor = Color.Transparent,
						focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
						unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
					),
				)
			}
		}
	}
}

@Composable
fun SearchList(
	data : List<Data>,
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

	Surface(
		modifier = Modifier
			.fillMaxSize()
	) {
		Box(
			modifier = Modifier
				.fillMaxWidth(),
			contentAlignment = Alignment.Center
		) {
			LazyColumn(
				state = listState,
				modifier = Modifier
					.fillMaxWidth()
					.padding(8.dp)) {

				items(data) {anime ->
					ListItemColumn(data = anime, onItemClick = {
						// navController.navigate(Screen.AnimeDetailScreen.route+"/${anime.node.id}")
					})
				}
			}

			if (viewModel.state.value.isLoading) {
				CircularProgressIndicator()
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
			viewModelModule,
			repositoryModule,
			useCaseModule,
			apiServiceModule
		)
	}) {
		SearchScreen(
			navController = rememberNavController()
		)
	}
}