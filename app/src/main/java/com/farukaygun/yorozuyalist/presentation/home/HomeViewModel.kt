package com.farukaygun.yorozuyalist.presentation.home

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukaygun.yorozuyalist.domain.model.RefreshToken
import com.farukaygun.yorozuyalist.domain.use_case.LoginUseCase
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.SharedPrefsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	private val loginUseCase: LoginUseCase
) : ViewModel() {
	private val _state = mutableStateOf(HomeState())
	val state: State<HomeState> = _state

	private var job: Job? = null

	fun isLoggedIn(context: Context) : Boolean {
		val sharedPrefsHelper = SharedPrefsHelper(context)
		val accessToken = sharedPrefsHelper.getString("accessToken")

		val dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
		val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
		val expiresInString = dateFormat.format(sharedPrefsHelper.getLong("expiresIn"))
		val currentTimeString = dateFormat.format(System.currentTimeMillis())
		val expiresIn = LocalDateTime.parse(expiresInString, dateTimeFormatter)
		val currentTime = LocalDateTime.parse(currentTimeString, dateTimeFormatter)

		if (accessToken.isEmpty() || currentTime > expiresIn) {
			clearToken(context)
			return false
		}

		val dateDiff = Duration.between(currentTime, expiresIn).toDays()
		if (dateDiff < 7)
			getRefreshToken(context)

		return true
	}

	private fun getRefreshToken(context: Context) {
		val grantType = "refresh_token"
		val refreshToken = SharedPrefsHelper(context).getString("refreshToken")
		job = loginUseCase.executeRefreshToken(
			grantType,
			refreshToken
		).onEach {
			when (it) {
				is Resource.Success -> {
					_state.value = HomeState(
						refreshToken = it.data,
						isLoading = false,
						error = ""
					)
				}
				is Resource.Error -> {
					// TODO: What should do here?
				}
				is Resource.Loading -> {
					_state.value = HomeState(isLoading = true)
				}
			}
		}.launchIn(viewModelScope)
	}

	fun saveRefreshToken(context: Context, refreshToken: RefreshToken) {
		val sharedPrefsHelper = SharedPrefsHelper(context)
		val expiresIn = System.currentTimeMillis() + refreshToken.expiresIn * 1000

		sharedPrefsHelper.saveString("accessToken", refreshToken.accessToken)
		sharedPrefsHelper.saveLong("expiresIn", expiresIn)
		sharedPrefsHelper.saveString("refreshToken", refreshToken.refreshToken)
		sharedPrefsHelper.saveBool("isLoggedIn", true)
	}

	private fun clearToken(context: Context)
	{
		val sharedPrefsHelper = SharedPrefsHelper(context)

		sharedPrefsHelper.removeKey("accessToken")
		sharedPrefsHelper.removeKey("expiresIn")
		sharedPrefsHelper.removeKey("refreshToken")
		sharedPrefsHelper.removeKey("isLoggedIn")
	}
}