package com.farukaygun.yorozuyalist.presentation.login.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
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
import androidx.navigation.NavController
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.presentation.Screen
import com.farukaygun.yorozuyalist.presentation.login.LoginEvent
import com.farukaygun.yorozuyalist.presentation.login.LoginViewModel
import com.farukaygun.yorozuyalist.ui.theme.caveatBrush
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
	navController: NavController,
	viewModel: LoginViewModel = koinViewModel()
) {
	val state = viewModel.state.value
	val context = LocalContext.current

	LaunchedEffect(state.authToken) {
		state.authToken?.let {
			viewModel.saveToken(context, it)
			navController.navigate(Screen.HomeScreen.route)
		}
	}

	Surface(
		modifier = Modifier
			.fillMaxSize()
			.background(Color.White)
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
					text = stringResource(R.string.login),
					fontSize = 16.sp,
					color = Color.White
				)
			}

			Spacer(modifier = Modifier.height(32.dp))

			if (state.error.isNotEmpty()) {
				Text(
					text = state.error,
					fontSize = 16.sp,
					color = Color.Red,
					textAlign = TextAlign.Center,
					modifier = Modifier.padding(16.dp)
				)

				Spacer(modifier = Modifier.height(32.dp))
			}

			CircularProgressIndicator(
				modifier = Modifier
					.alpha(if (state.isLoading) 1f else 0f)
					.width(32.dp)
					.height(32.dp)
			)
		}
	}
}

@Preview
@Composable
fun LoginScreenPreview() {
}