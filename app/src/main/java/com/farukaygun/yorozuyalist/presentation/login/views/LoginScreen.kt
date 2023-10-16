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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.presentation.Screen
import com.farukaygun.yorozuyalist.presentation.common.CommonUI
import com.farukaygun.yorozuyalist.presentation.login.LoginEvent
import com.farukaygun.yorozuyalist.presentation.login.LoginState
import com.farukaygun.yorozuyalist.presentation.login.LoginViewModel
import com.farukaygun.yorozuyalist.ui.theme.caveatBrush

@Composable
fun LoginScreen(
	navController: NavController,
	viewModel: LoginViewModel = hiltViewModel()
) {
	val context = LocalContext.current
	val state = viewModel.state.value

	LoginScreenContent(
		navController,
		state,
		onLoginClick = { viewModel.onEvent(LoginEvent.Login(context)) },
		saveToken = { viewModel.saveToken() }
	)
}

@Composable
fun LoginScreenContent(
	navController: NavController,
	state: LoginState,
	onLoginClick: () -> Unit,
	saveToken: () -> Unit
) {
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
				contentDescription = stringResource(R.string.app_icon_desc),
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
				onClick = { onLoginClick() },
				modifier = Modifier.width(128.dp)
			) {

				Text(
					text = stringResource(R.string.login),
					fontSize = 16.sp,
					color = Color.White
				)
			}

			if (state.error.isNotEmpty()) {
				Text(
					text = state.error,
					fontSize = 16.sp,
					color = Color.Red,
					textAlign = TextAlign.Justify,
					modifier = Modifier.padding(16.dp)
				)
			}

			Column(modifier = Modifier.alpha(if (state.isLoading) 1f else 0f)) {
				CommonUI.IndeterminateCircularIndicator()
			}
		}
	}

	LaunchedEffect(state.authToken) {
		saveToken()
		state.authToken?.let {
			navController.navigate(Screen.HomeScreen.route)
		}
	}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
	LoginScreenContent(
		navController = rememberNavController(),
		state = LoginState(),
		onLoginClick = {},
		saveToken = {}
	)
}