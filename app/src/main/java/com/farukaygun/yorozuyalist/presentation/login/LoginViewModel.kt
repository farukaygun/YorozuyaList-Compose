package com.farukaygun.yorozuyalist.presentation.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukaygun.yorozuyalist.domain.model.AuthToken
import com.farukaygun.yorozuyalist.domain.use_case.LoginUseCase
import com.farukaygun.yorozuyalist.util.Constants
import com.farukaygun.yorozuyalist.util.Extensions.openInCustomTabs
import com.farukaygun.yorozuyalist.util.Private
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
	private val loginUseCase: LoginUseCase
) : ViewModel() {

	private val _state = mutableStateOf(LoginState())
	val state: State<LoginState> = _state

	private val codeVerifier = Util.generateCodeChallenge()
	private val authState = "login"
	val loginUrl =
		"${Constants.AUTH_URL}/authorize?response_type=code&client_id=${Private.CLIENT_ID}&code_challenge=${codeVerifier}&state=${authState}"

	private fun getAuthToken() {
		loginUseCase.executeAuthToken().onEach {
			when (it) {
				is Resource.Success -> {
					_state.value = LoginState(authToken = it.data ?: AuthToken("", 0, "", ""))
				}

				is Resource.Error -> {
					_state.value = LoginState(error = it.message ?: "Login Error!")
				}

				is Resource.Loading -> {
					_state.value = LoginState(isLoading = true)
				}

				else -> {}
			}
		}.launchIn(viewModelScope)
	}

	fun openInCustomTabs(context: Context, url: String) {
		CustomTabsIntent.Builder()
			.build().apply {
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
				launchUrl(context, Uri.parse(url))
			}
	}

	private fun parseIntentData(intent: Intent) {

	}
}