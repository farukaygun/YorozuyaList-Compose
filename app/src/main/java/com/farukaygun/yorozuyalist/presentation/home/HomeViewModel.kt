package com.farukaygun.yorozuyalist.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.model.Data
import com.farukaygun.yorozuyalist.domain.model.RefreshToken
import com.farukaygun.yorozuyalist.domain.use_case.AnimeUseCase
import com.farukaygun.yorozuyalist.domain.use_case.LoginUseCase
import com.farukaygun.yorozuyalist.util.Calendar.Companion.getYearAndSeason
import com.farukaygun.yorozuyalist.util.Calendar.Companion.weekDayJapan
import com.farukaygun.yorozuyalist.util.Constants
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.SharedPrefsHelper
import com.farukaygun.yorozuyalist.util.StringValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HomeViewModel(
	private val loginUseCase: LoginUseCase,
	private val animeUseCase: AnimeUseCase,
	private val sharedPrefsHelper: SharedPrefsHelper
) : ViewModel() {
	private val _state = mutableStateOf(HomeState())
	val state: State<HomeState> = _state

	private var job: Job? = null

	fun isLoggedIn() : Boolean {
		val accessToken = sharedPrefsHelper.getString("accessToken")

		val expiresInAsMillis = sharedPrefsHelper.getLong("expiresIn")
		val expiresInInstant = Instant.fromEpochMilliseconds(expiresInAsMillis)
		val expiresInLocalDateTime = expiresInInstant.toLocalDateTime(TimeZone.currentSystemDefault())
		val currentDateTimeInstant = Clock.System.now()
		val currentLocalDateTime = currentDateTimeInstant.toLocalDateTime(TimeZone.currentSystemDefault())

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
		}
		else _state.value.refreshToken = RefreshToken(accessToken, sharedPrefsHelper.getLong("expiresIn"), sharedPrefsHelper.getString("refreshToken"), "")

		return true
	}

	private fun getRefreshToken() {
		val grantType = "refresh_token"
		val refreshToken = sharedPrefsHelper.getString("refreshToken")
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
					_state.value = HomeState(
						error = it.message ?: StringValue.StringResource(R.string.token_refresh_error)
							.toString(),
						isLoading = false
					)
				}
				is Resource.Loading -> {
					_state.value = HomeState(isLoading = true)
				}
			}
		}.launchIn(viewModelScope)
	}

	private fun saveRefreshToken(refreshToken: RefreshToken) {
		val expiresIn = System.currentTimeMillis() + refreshToken.expiresIn * 1000

		sharedPrefsHelper.saveString("accessToken", refreshToken.accessToken)
		sharedPrefsHelper.saveLong("expiresIn", expiresIn)
		sharedPrefsHelper.saveString("refreshToken", refreshToken.refreshToken)
		sharedPrefsHelper.saveBool("isLoggedIn", true)
	}

	private fun clearToken()
	{
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

		job = animeUseCase.executeSeasonalAnime(year, season.value, limit)
			.flowOn(Dispatchers.IO)
			.onEach {
				when(it) {
					is Resource.Success -> {
						it.data?.data?.forEach {anime ->
							if (anime.node.broadcast?.dayOfTheWeek.equals(weekDayJapan.toString(), true) && anime.node.status == Constants.CURRENTLY_AIRING)
								animeList.add(anime)
						}

						animeList.let {animeList ->
							_state.value =_state.value.copy(
								animeTodayList = animeList,
								isLoading = false,
								error = ""
							)
						}
					}
					is Resource.Error -> {
						_state.value = HomeState(
							error = it.message ?: StringValue.StringResource(R.string.error_fetching).toString(),
						)
					}
					is Resource.Loading -> {
						_state.value = _state.value.copy(isLoading = true)
					}
				}
			}.launchIn(viewModelScope)
	}


	private fun getSeasonalAnime() {
		val (year, season) = getYearAndSeason()
		job = animeUseCase.executeSeasonalAnime(year, season.value)
			.flowOn(Dispatchers.IO)
			.onEach {
				when (it) {
					is Resource.Success -> {
						_state.value = _state.value.copy(
							animeSeasonalList = it.data?.data ?: emptyList(),
							isLoading = false,
							error = ""
						)
					}
					is Resource.Error -> {
						_state.value = _state.value.copy(
							error = it.message ?: StringValue.StringResource(R.string.error_fetching).toString(),
						)
					}
					is Resource.Loading -> {
						_state.value = _state.value.copy(isLoading = true)
					}
				}
			}.launchIn(viewModelScope)
	}

	private fun getSuggestedAnime() {
		job = animeUseCase.executeSuggestedAnime()
			.flowOn(Dispatchers.IO)
			.onEach {
				when (it) {
					is Resource.Success -> {
						_state.value = _state.value.copy(
							animeSuggestionList = it.data?.data ?: emptyList(),
							isLoading = false,
							error = ""
						)
					}
					is Resource.Error -> {
						_state.value = _state.value.copy(
							error = it.message ?: StringValue.StringResource(R.string.error_fetching).toString(),
						)
					}
					is Resource.Loading -> {
						_state.value = _state.value.copy(isLoading = true)
					}
				}
			}.launchIn(viewModelScope)
	}

	fun onEvent(event: HomeEvent) {
		when (event) {
			is HomeEvent.InitRequestChain -> {
				getTodayAnime()
				getSeasonalAnime()
				getSuggestedAnime()
			}
		}
	}
}