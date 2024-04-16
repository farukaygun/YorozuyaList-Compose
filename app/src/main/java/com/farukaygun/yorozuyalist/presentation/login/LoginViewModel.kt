package com.farukaygun.yorozuyalist.presentation.login

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.model.AuthToken
import com.farukaygun.yorozuyalist.domain.use_case.LoginUseCase
import com.farukaygun.yorozuyalist.util.Constants
import com.farukaygun.yorozuyalist.util.Constants.YOROZUYA_PAGELINK
import com.farukaygun.yorozuyalist.util.CustomExtensions.openCustomTab
import com.farukaygun.yorozuyalist.util.Private
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.SharedPrefsHelper
import com.farukaygun.yorozuyalist.util.StringValue
import com.farukaygun.yorozuyalist.util.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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
	private val loginUrl =
		"${Constants.OAUTH2_URL}authorize?response_type=code&client_id=${Private.CLIENT_ID}&code_challenge=${codeVerifier}&state=${authState}"

	private var job: Job? = null

	fun parseIntentData(intent: Intent?) {
		if (intent?.data?.toString()?.startsWith(YOROZUYA_PAGELINK) == true) {
			intent.data?.let {
				val code = it.getQueryParameter("code")
				if (code != null) {
					getAccessToken(code)
				}
			}
		}
	}

	private fun getAccessToken(code: String) {
		job = loginUseCase.executeAuthToken(
			code,
			Private.CLIENT_ID,
			codeVerifier,
		).onEach {
			when (it) {
				is Resource.Success -> {
					_state.value = LoginState(
						authToken = it.data,
						isLoading = false,
						error = ""
					)
				}
				is Resource.Error -> {
					_state.value = LoginState(
						error = it.message ?: StringValue.StringResource(R.string.user_login_error)
							.toString(),
						isLoading = false
					)
				}
				is Resource.Loading -> {
					_state.value = LoginState(isLoading = true)
				}
			}
		}.launchIn(viewModelScope)
	}

	fun saveToken(context: Context, authToken: AuthToken) {
		val sharedPrefsHelper = SharedPrefsHelper(context)
		val expiresIn = System.currentTimeMillis() + authToken.expiresIn * 1000

		sharedPrefsHelper.saveString("accessToken", authToken.accessToken)
		sharedPrefsHelper.saveLong("expiresIn", expiresIn)
		sharedPrefsHelper.saveString("refreshToken", authToken.refreshToken)
		sharedPrefsHelper.saveBool("isLoggedIn", true)
	}

	fun onEvent(event: LoginEvent) {
		when (event) {
			is LoginEvent.Login -> {
				event.context.openCustomTab(loginUrl)
			}
		}
	}
}