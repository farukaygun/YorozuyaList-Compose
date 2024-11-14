package com.farukaygun.yorozuyalist.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.RefreshToken
import com.farukaygun.yorozuyalist.domain.models.enums.MediaStatus
import com.farukaygun.yorozuyalist.domain.use_case.AnimeUseCase
import com.farukaygun.yorozuyalist.domain.use_case.LoginUseCase
import com.farukaygun.yorozuyalist.presentation.base.BaseViewModel
import com.farukaygun.yorozuyalist.util.Calendar.Companion.getYearAndSeason
import com.farukaygun.yorozuyalist.util.Calendar.Companion.weekDayJapan
import com.farukaygun.yorozuyalist.util.SharedPrefsHelper
import com.farukaygun.yorozuyalist.util.StringValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HomeViewModel(
	private val loginUseCase: LoginUseCase,
	private val animeUseCase: AnimeUseCase,
	private val sharedPrefsHelper: SharedPrefsHelper
) : BaseViewModel<HomeState>() {
	override val _state = mutableStateOf(HomeState())

	init {
		onEvent(HomeEvent.InitRequestChain)
	}

	fun isLoggedIn(): Boolean {
		val accessToken = sharedPrefsHelper.getString("accessToken")

		val expiresInAsMillis = sharedPrefsHelper.getLong("expiresIn")
		val expiresInInstant = Instant.fromEpochMilliseconds(expiresInAsMillis)
		val expiresInLocalDateTime =
			expiresInInstant.toLocalDateTime(TimeZone.currentSystemDefault())
		val currentDateTimeInstant = Clock.System.now()
		val currentLocalDateTime =
			currentDateTimeInstant.toLocalDateTime(TimeZone.currentSystemDefault())

		if (accessToken.isEmpty() || currentLocalDateTime > expiresInLocalDateTime) {
			clearToken()
			return false
		}

		val dateDiff = expiresInInstant.minus(currentDateTimeInstant).inWholeDays

		if (dateDiff < 7) {
			getRefreshToken()
			_state.value.refreshToken?.let {
				saveRefreshToken(it)
			}
		} else _state.value.refreshToken = RefreshToken(
			accessToken,
			sharedPrefsHelper.getLong("expiresIn"),
			sharedPrefsHelper.getString("refreshToken"),
			""
		)

		return true
	}

	private suspend fun initRequestChain() {
		_state.value = _state.value.copy(isLoading = true)

		getTodayAnime()
		getSeasonalAnime()
		getSuggestedAnime()

		jobs.forEach { it.join() }
		_state.value = _state.value.copy(isLoading = false)
	}

	private fun getRefreshToken() {
		val grantType = "refresh_token"
		val refreshToken = sharedPrefsHelper.getString("refreshToken")

		jobs += loginUseCase.executeRefreshToken(
			grantType,
			refreshToken
		)
			.flowOn(Dispatchers.IO)
			.handleResource(
				onSuccess = { token ->
					_state.value = _state.value.copy(
						refreshToken = token,
						error = ""
					)
				},
				onError = { error ->
					_state.value = _state.value.copy(
						error = error
							?: StringValue.StringResource(R.string.token_refresh_error).toString(),
						isLoading = false
					)
				}
			)
	}

	private fun saveRefreshToken(refreshToken: RefreshToken) {
		val expiresIn = System.currentTimeMillis() + refreshToken.expiresIn * 1000

		sharedPrefsHelper.saveString("accessToken", refreshToken.accessToken)
		sharedPrefsHelper.saveLong("expiresIn", expiresIn)
		sharedPrefsHelper.saveString("refreshToken", refreshToken.refreshToken)
		sharedPrefsHelper.saveBool("isLoggedIn", true)
	}

	private fun clearToken() {
		sharedPrefsHelper.removeKey("accessToken")
		sharedPrefsHelper.removeKey("expiresIn")
		sharedPrefsHelper.removeKey("refreshToken")
		sharedPrefsHelper.removeKey("isLoggedIn")
	}

	private fun getTodayAnime(
		limit: Int = 500
	) {
		val (year, season) = getYearAndSeason()
		val animeList = mutableListOf<Data>()

		jobs += animeUseCase.executeSeasonalAnime(year, season.value, limit)
			.flowOn(Dispatchers.IO)
			.handleResource(
				onSuccess = { animeData ->
					animeData?.data?.forEach { anime ->
						if (anime.node.broadcast?.dayOfTheWeek.equals(
								weekDayJapan.toString(),
								true
							) && anime.node.status == MediaStatus.CURRENTLY_AIRING.formatForApi()
						)
							animeList.add(anime)
					}.apply {
						_state.value = _state.value.copy(
							animeTodayList = animeList,
							error = ""
						)
					}
				},
				onError = { error ->
					_state.value = HomeState(
						error = error
							?: StringValue.StringResource(R.string.error_fetching).toString(),
						isLoading = false
					)
				}
			)
	}


	private fun getSeasonalAnime() {
		val (year, season) = getYearAndSeason()

		jobs += animeUseCase.executeSeasonalAnime(year, season.value)
			.flowOn(Dispatchers.IO)
			.handleResource(
				onSuccess = { animeData ->
					_state.value = _state.value.copy(
						animeSeasonalList = animeData?.data ?: emptyList(),
						error = ""
					)
				},
				onError = { error ->
					_state.value = _state.value.copy(
						error = error
							?: StringValue.StringResource(R.string.error_fetching).toString(),
						isLoading = false
					)
				}
			)
	}

	private fun getSuggestedAnime() {
		jobs += animeUseCase.executeSuggestedAnime()
			.flowOn(Dispatchers.IO)
			.handleResource(
				onSuccess = { animeData ->
					_state.value = _state.value.copy(
						animeSuggestionList = animeData?.data ?: emptyList(),
						error = ""
					)
				},
				onError = { error ->
					_state.value = _state.value.copy(
						error = error
							?: StringValue.StringResource(R.string.error_fetching).toString(),
						isLoading = false
					)
				}
			)
	}

	fun onEvent(event: HomeEvent) {
		when (event) {
			is HomeEvent.InitRequestChain -> viewModelScope.launch { initRequestChain() }
		}
	}
}