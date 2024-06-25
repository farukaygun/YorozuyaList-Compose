package com.farukaygun.yorozuyalist.presentation.manga_list.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.farukaygun.yorozuyalist.presentation.manga_list.MangaListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MangaListScreen(
	navController: NavController,
	viewModel: MangaListViewModel = koinViewModel()
) {
	val state = viewModel.state.value

	Column(
		modifier = Modifier
			.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(text = "Manga List Screen")
	}
}