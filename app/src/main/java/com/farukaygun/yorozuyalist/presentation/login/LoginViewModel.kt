package com.farukaygun.yorozuyalist.presentation.login

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.models.AccessToken
import com.farukaygun.yorozuyalist.domain.use_case.LoginUseCase
import com.farukaygun.yorozuyalist.presentation.base.BaseViewModel
import com.farukaygun.yorozuyalist.util.Constants
import com.farukaygun.yorozuyalist.util.Constants.YOROZUYA_PAGE_LINK
import com.farukaygun.yorozuyalist.util.Extensions.CustomExtensions.openCustomTab
import com.farukaygun.yorozuyalist.util.Private
import com.farukaygun.yorozuyalist.util.SharedPrefsHelper
import com.farukaygun.yorozuyalist.util.StringValue
import com.farukaygun.yorozuyalist.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class LoginViewModel(
	private val loginUseCase: LoginUseCase,
	private val sharedPrefsHelper: SharedPrefsHelper
) : BaseViewModel<LoginState>() {
	override val _state = mutableStateOf(LoginState())

	private val codeVerifier = Util.generateCodeChallenge()
	private val authState = "login"
	private val loginUrl =
		"${Constants.OAUTH2_URL}authorize?response_type=code&client_id=${Private.CLIENT_ID}&code_challenge=${codeVerifier}&state=${authState}"

	private fun parseIntentData(context: Context, intent: Intent?) {
		if (intent?.data?.toString()?.startsWith(YOROZUYA_PAGE_LINK) == true) {
			intent.data?.let {
				val code = it.getQueryParameter("code")
				if (code != null) {
					getAccessToken(context, code)
				} else {
					_state.value = _state.value.copy(
						error = StringValue.StringResource(R.string.login_code_error)
							.asString(context)
					)
				}
			}
		}
	}

	private fun getAccessToken(context: Context, code: String) {
		jobs += loginUseCase.executeAuthToken(
			code,
			Private.CLIENT_ID,
			codeVerifier,
		)
			.flowOn(Dispatchers.IO)
			.handleResource(
				onSuccess = { accessToken ->
					_state.value = _state.value.copy(
						accessToken = accessToken,
						isLoading = false,
						error = ""
					)
				},
				onError = {
					_state.value = _state.value.copy(
						error = it ?: StringValue.StringResource(R.string.user_login_error)
							.asString(context),
						isLoading = false
					)
				},
				onLoading = { _state.value = _state.value.copy(isLoading = true) }
			)
	}

	private fun saveToken(accessToken: AccessToken) {
		val expiresIn = System.currentTimeMillis() + accessToken.expiresIn * 1000

		sharedPrefsHelper.saveString("accessToken", accessToken.accessToken)
		sharedPrefsHelper.saveLong("expiresIn", expiresIn)
		sharedPrefsHelper.saveString("refreshToken", accessToken.refreshToken)
		sharedPrefsHelper.saveBool("isLoggedIn", true)
	}

	fun onEvent(event: LoginEvent) {
		when (event) {
			is LoginEvent.Login -> {
				event.context.openCustomTab(loginUrl)
			}

			is LoginEvent.ParseIntentData -> {
				parseIntentData(event.context, event.intent)
			}

			is LoginEvent.SaveToken -> {
				saveToken(event.accesToken)
			}
		}
	}
}