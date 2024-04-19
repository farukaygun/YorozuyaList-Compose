package com.farukaygun.yorozuyalist.presentation.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farukaygun.yorozuyalist.presentation.Screen
import com.farukaygun.yorozuyalist.presentation.home.HomeViewModel

@Composable
fun HomeScreen(
	navController: NavController,
	viewModel: HomeViewModel = hiltViewModel()
) {
	val state = viewModel.state.value
	val context = LocalContext.current

	LaunchedEffect(Unit) {
		if (!viewModel.isLoggedIn(context))
			navController.navigate(Screen.LoginScreen.route)
	}

	LaunchedEffect(state.refreshToken) {
		state.refreshToken?.let {
			viewModel.saveRefreshToken(context, it)
		}
	}

	Surface(
		modifier = Modifier
			.fillMaxSize()
	) {
		Box(
			modifier = Modifier.fillMaxSize()
				.background(Color.Black),
			contentAlignment = Alignment.Center
		) {

//			Text(
//				text = "Home Screen",
//				modifier = Modifier
//					.fillMaxSize(),
//				color = Color.White,
//				textAlign = TextAlign.Center
//			)
		}
	}
}