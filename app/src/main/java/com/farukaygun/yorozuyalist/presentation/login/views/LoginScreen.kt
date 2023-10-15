package com.farukaygun.yorozuyalist.presentation.login.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.presentation.Screen
import com.farukaygun.yorozuyalist.presentation.login.LoginEvent
import com.farukaygun.yorozuyalist.presentation.login.LoginState
import com.farukaygun.yorozuyalist.presentation.login.LoginViewModel
import com.farukaygun.yorozuyalist.ui.theme.caveatBrush
import com.farukaygun.yorozuyalist.util.CustomExtensions.openCustomTab
import com.farukaygun.yorozuyalist.util.SharedPrefsHelper
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LoginScreen(
	navController: NavController,
	viewModel: LoginViewModel = hiltViewModel()
) {
	val state = viewModel.state.value
	val context = LocalContext.current

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(Color.White),
		contentAlignment = Alignment.Center
	) {
		Column(
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally,
		) {

			Image(
				painter = painterResource(id = R.drawable.icon),
				contentDescription = "Yorozuya List Icon",
				modifier = Modifier.fillMaxWidth(.5f)
			)

			Spacer(modifier = Modifier.height(16.dp))

			Text(
				text = "YOROZUYA LIST",
				fontFamily = caveatBrush,
				fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
				fontSize = 34.sp
			)

			Spacer(modifier = Modifier.height(32.dp))

			Button(
				onClick = { viewModel.onEvent(LoginEvent.Login(context)) },
				modifier = Modifier.width(128.dp)
			) {

				Text(
					text = "Login",
					fontSize = 16.sp,
					color = Color.White
				)
0			}

			Spacer(modifier = Modifier.height(16.dp))

			println("login: $viewModel")
			if (state.isLoading) {
				CircularProgressIndicator()
			}
		}
	}
}

@Preview
@Composable
fun LoginScreenPreview() {
}