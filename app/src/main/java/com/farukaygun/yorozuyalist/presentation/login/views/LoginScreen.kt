package com.farukaygun.yorozuyalist.presentation.login.views

import android.app.Activity
import android.graphics.fonts.FontFamily
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.farukaygun.yorozuyalist.domain.repository.LoginRepositoryImpl
import com.farukaygun.yorozuyalist.presentation.login.LoginViewModel
import com.farukaygun.yorozuyalist.ui.theme.caveatBrush
import dagger.hilt.android.qualifiers.ActivityContext

@Composable
fun LoginScreen(
	navController: NavController,
	viewModel: LoginViewModel = hiltViewModel()
) {
	LoginScreenContent(navController, viewModel)
}

@Composable
fun LoginScreenContent(navController: NavController, viewModel: LoginViewModel) {
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

			val context = LocalContext.current
			Button(
				onClick = { viewModel.openInCustomTabs(context, viewModel.loginUrl) },
				modifier = Modifier.width(128.dp)
			) {

				Text(
					text = "Login",
					fontSize = 16.sp,
					color = Color.White
				)
			}
		}
	}
}

@Preview
@Composable
fun LoginScreenPreview() {
}