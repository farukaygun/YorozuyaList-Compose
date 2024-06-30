package com.farukaygun.yorozuyalist.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.model.Stat
import com.farukaygun.yorozuyalist.domain.use_case.UserUseCase
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.Status
import com.farukaygun.yorozuyalist.util.StringValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProfileViewModel(
	private val user: UserUseCase
) : ViewModel() {
	private val _state = mutableStateOf(ProfileState())
	val state: State<ProfileState> = _state

	private var job: Job? = null

	private fun getUserProfile() {
		job = user.executeUser()
			.flowOn(Dispatchers.IO)
			.onEach {
				when (it) {
					is Resource.Success -> {
						val tempStatList = mutableListOf<Stat>()
						it.data?.let { data ->
							tempStatList.add(Stat(
								type = Status.WATCHING,
								value = data.userAnimeStatistics.numItemsWatching
							))

							tempStatList.add(Stat(
								type = Status.COMPLETED,
								value = data.userAnimeStatistics.numItemsCompleted
							))

							tempStatList.add(Stat(
								type = Status.ON_HOLD,
								value = data.userAnimeStatistics.numItemsOnHold
							))

							tempStatList.add(Stat(
								type = Status.DROPPED,
								value = data.userAnimeStatistics.numItemsDropped
							))

							tempStatList.add(Stat(
								type = Status.PLAN_TO_WATCH,
								value = data.userAnimeStatistics.numItemsPlanToWatch
							))
						}

						_state.value = _state.value.copy(
							profileData = it.data,
							animeStats = tempStatList,
							isLoading = false,
							error = ""
						)
					}

					is Resource.Error -> {
						_state.value = _state.value.copy(
							error = it.message
								?: StringValue.StringResource(R.string.error_fetching).toString(),
							isLoading = false
						)
					}

					is Resource.Loading -> {
						_state.value = _state.value.copy(isLoading = true)
					}
				}
			}.launchIn(viewModelScope)

	}

	fun onEvent(event: ProfileEvent) {
		when (event) {
			is ProfileEvent.InitRequestChain -> {
				getUserProfile()
			}
		}
	}
}