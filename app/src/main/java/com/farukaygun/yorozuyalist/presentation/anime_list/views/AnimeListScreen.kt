package com.farukaygun.yorozuyalist.presentation.anime_list.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.farukaygun.yorozuyalist.presentation.anime_list.AnimeListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AnimeListScreen(
	navController: NavController,
	viewModel: AnimeListViewModel = koinViewModel()
) {
	val state = viewModel.state.value

	Column(
		modifier = Modifier
			.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(text = "Anime List Screen")
	}
}