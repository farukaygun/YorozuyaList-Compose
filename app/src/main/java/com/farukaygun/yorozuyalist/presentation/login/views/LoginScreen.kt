package com.farukaygun.yorozuyalist.presentation.login.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.data.di.apiServiceModule
import com.farukaygun.yorozuyalist.data.di.repositoryModule
import com.farukaygun.yorozuyalist.data.di.useCaseModule
import com.farukaygun.yorozuyalist.data.di.viewModelModule
import com.farukaygun.yorozuyalist.presentation.Screen
import com.farukaygun.yorozuyalist.presentation.login.LoginEvent
import com.farukaygun.yorozuyalist.presentation.login.LoginViewModel
import com.farukaygun.yorozuyalist.ui.theme.appTitleFontFamily
import com.farukaygun.yorozuyalist.util.StringValue
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Composable
fun LoginScreen(
	navController: NavController,
	viewModel: LoginViewModel = koinViewModel()
) {
	val state = viewModel.state.value
	val context = LocalContext.current

	LaunchedEffect(state.accessToken) {
		state.accessToken?.let {
			viewModel.onEvent(LoginEvent.SaveToken(it))
			navController.navigate(Screen.HomeScreen.route)
		}
	}

	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {

		Image(
			painter = painterResource(id = R.drawable.icon),
			contentDescription = "Yorozuya List Icon",
			modifier = Modifier.fillMaxWidth(.5f)
		)

		Text(
			text = StringValue.StringResource(R.string.app_name)
				.asString(context)
				.toUpperCase(Locale.current),
			fontFamily = appTitleFontFamily,
			style = MaterialTheme.typography.headlineLarge,
			color = MaterialTheme.colorScheme.onBackground
		)

		Spacer(modifier = Modifier.height(32.dp))

		Button(
			colors = ButtonDefaults.buttonColors(
				containerColor = MaterialTheme.colorScheme.primary,
				contentColor = MaterialTheme.colorScheme.onPrimary
			),
			onClick = { viewModel.onEvent(LoginEvent.Login(context)) },
		) {

			Text(
				text = stringResource(R.string.login),
				style = MaterialTheme.typography.labelMedium,
			)
		}

		if (state.error.isNotEmpty()) {
			Text(
				text = state.error,
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onErrorContainer,
				textAlign = TextAlign.Center,
			)

			Spacer(modifier = Modifier.height(32.dp))
		}

		if (!state.isLoading) {
			CircularProgressIndicator()
		}

		if (state.error.isNotEmpty()) {
			Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_SHORT).show()
		}
	}
}

@Composable
@Preview
fun LoginScreenPreview() {
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
		LoginScreen(navController = rememberNavController())
	}
}