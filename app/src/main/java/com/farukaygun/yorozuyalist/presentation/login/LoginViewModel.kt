package com.farukaygun.yorozuyalist.presentation.login

import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.farukaygun.yorozuyalist.domain.models.AccessToken
import com.farukaygun.yorozuyalist.domain.models.RefreshToken
import com.farukaygun.yorozuyalist.domain.use_case.login.GetAccessTokenUseCase
import com.farukaygun.yorozuyalist.domain.use_case.login.GetRefreshTokenUseCase
import com.farukaygun.yorozuyalist.presentation.base.BaseViewModel
import com.farukaygun.yorozuyalist.util.AppError
import com.farukaygun.yorozuyalist.util.Constants
import com.farukaygun.yorozuyalist.util.Constants.YOROZUYA_PAGE_LINK
import com.farukaygun.yorozuyalist.util.Extensions.CustomExtensions.openCustomTab
import com.farukaygun.yorozuyalist.util.PrefKeys
import com.farukaygun.yorozuyalist.util.Private
import com.farukaygun.yorozuyalist.util.SharedPrefsHelper
import com.farukaygun.yorozuyalist.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class LoginViewModel(
	private val getAccessToken: GetAccessTokenUseCase,
	private val getRefreshToken: GetRefreshTokenUseCase,
	private val sharedPrefsHelper: SharedPrefsHelper
) : BaseViewModel<LoginState>() {
	override val _state = MutableStateFlow(LoginState())

	private val codeVerifier = Util.generateCodeChallenge()
	private val authState = "login"
	private val loginUrl =
		"${Constants.OAUTH2_URL}authorize?response_type=code&client_id=${Private.CLIENT_ID}&code_challenge=${codeVerifier}&state=${authState}"

	init {
		isLoggedIn()
	}

	fun isLoggedIn(): Boolean {
		val accessToken = sharedPrefsHelper.getString(PrefKeys.ACCESS_TOKEN)
		if (accessToken.isEmpty()) return false

		val expiresInAsMillis = sharedPrefsHelper.getLong(PrefKeys.EXPIRES_IN)
		if (expiresInAsMillis == 0L) return false

		val currentTimeMillis = kotlin.time.Clock.System.now().toEpochMilliseconds()

		if (currentTimeMillis > expiresInAsMillis) {
			clearToken()
			return false
		}

		val daysTillExpire = (expiresInAsMillis - currentTimeMillis) / (24 * 60 * 60 * 1000)
		if (daysTillExpire < 7) {
			viewModelScope.launch {
				getRefreshToken()
				_state.value.refreshToken?.let { token ->
					saveRefreshToken(token)
				}
			}
		} else {
			_state.value = _state.value.copy(
				refreshToken = RefreshToken(
					accessToken, expiresInAsMillis, sharedPrefsHelper.getString(PrefKeys.REFRESH_TOKEN), ""
				)
			)
		}

		return true
	}

	private fun getRefreshToken() {
		val grantType = "refresh_token"
		val refreshToken = sharedPrefsHelper.getString(PrefKeys.REFRESH_TOKEN)

		jobs += getRefreshToken(
			grantType,
			refreshToken
		)
			.flowOn(Dispatchers.IO)
			.handleResource(
				onSuccess = { token ->
					_state.value = _state.value.copy(
						refreshToken = token,
						error = null
					)
				},
				onError = { error ->
					_state.value = _state.value.copy(
						error = error,
						isLoading = false
					)
				}
			)
	}

	private fun saveRefreshToken(refreshToken: RefreshToken) {
		val expiresIn = System.currentTimeMillis() + refreshToken.expiresIn * 1000

		sharedPrefsHelper.saveString(PrefKeys.ACCESS_TOKEN, refreshToken.accessToken)
		sharedPrefsHelper.saveLong(PrefKeys.EXPIRES_IN, expiresIn)
		sharedPrefsHelper.saveString(PrefKeys.REFRESH_TOKEN, refreshToken.refreshToken)
		sharedPrefsHelper.saveBool(PrefKeys.IS_LOGGED_IN, true)
	}

	private fun clearToken() {
		sharedPrefsHelper.removeKey(PrefKeys.ACCESS_TOKEN)
		sharedPrefsHelper.removeKey(PrefKeys.EXPIRES_IN)
		sharedPrefsHelper.removeKey(PrefKeys.REFRESH_TOKEN)
		sharedPrefsHelper.removeKey(PrefKeys.IS_LOGGED_IN)
	}

	private fun parseIntentData(intent: Intent?) {
		if (intent?.data?.toString()?.startsWith(YOROZUYA_PAGE_LINK) == true) {
			intent.data?.let {
				val code = it.getQueryParameter("code")
				if (code != null) {
					getAccessToken(code)
				} else {
					_state.value = _state.value.copy(
						error = AppError.UnknownError("Error during user login: 'code' parameter not found.")
					)
				}
			}
		}
	}

	private fun getAccessToken(code: String) {
		jobs += getAccessToken(
			code,
			Private.CLIENT_ID,
			codeVerifier,
		)
			.flowOn(Dispatchers.IO)
			.handleResource(
				onSuccess = { accessToken ->
					_state.value = _state.value.copy(
						accessToken = accessToken,
						error = null
					)
				},
				onError = {
					_state.value = _state.value.copy(
						error = it,
						isLoading = false
					)
				},
				onLoading = { _state.value = _state.value.copy(isLoading = true) }
			)
	}

	private fun saveToken(accessToken: AccessToken) {
		val expiresIn = System.currentTimeMillis() + accessToken.expiresIn * 1000

		sharedPrefsHelper.saveString(PrefKeys.ACCESS_TOKEN, accessToken.accessToken)
		sharedPrefsHelper.saveLong(PrefKeys.EXPIRES_IN, expiresIn)
		sharedPrefsHelper.saveString(PrefKeys.REFRESH_TOKEN, accessToken.refreshToken)
		sharedPrefsHelper.saveBool(PrefKeys.IS_LOGGED_IN, true)
	}

	fun onEvent(event: LoginEvent) {
		when (event) {
			is LoginEvent.Login -> {
				event.context.openCustomTab(loginUrl)
			}

			is LoginEvent.ParseIntentData -> {
				parseIntentData(event.intent)
			}

			is LoginEvent.SaveToken -> {
				saveToken(event.accesToken)
			}
		}
	}
}
