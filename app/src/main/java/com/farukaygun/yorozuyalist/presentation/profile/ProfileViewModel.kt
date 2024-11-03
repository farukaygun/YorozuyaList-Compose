package com.farukaygun.yorozuyalist.presentation.profile

import androidx.compose.runtime.mutableStateOf
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.models.Stat
import com.farukaygun.yorozuyalist.domain.models.enums.MyListMediaStatus
import com.farukaygun.yorozuyalist.domain.use_case.UserUseCase
import com.farukaygun.yorozuyalist.presentation.base.BaseViewModel
import com.farukaygun.yorozuyalist.util.StringValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class ProfileViewModel(
	private val user: UserUseCase
) : BaseViewModel<ProfileState>() {
	override val _state = mutableStateOf(ProfileState())

	init {
		onEvent(ProfileEvent.InitRequestChain)
	}

	private fun getUserProfile() {
		jobs += user.executeUser()
			.flowOn(Dispatchers.IO)
			.handleResource(
				onSuccess = { userData ->
					val tempStatList = mutableListOf<Stat>()
					userData?.let { data ->
						tempStatList.add(
							Stat(
								type = MyListMediaStatus.WATCHING,
								value = data.userAnimeStatistics.numItemsWatching
							)
						)

						tempStatList.add(
							Stat(
								type = MyListMediaStatus.COMPLETED,
								value = data.userAnimeStatistics.numItemsCompleted
							)
						)

						tempStatList.add(
							Stat(
								type = MyListMediaStatus.ON_HOLD,
								value = data.userAnimeStatistics.numItemsOnHold
							)
						)

						tempStatList.add(
							Stat(
								type = MyListMediaStatus.DROPPED,
								value = data.userAnimeStatistics.numItemsDropped
							)
						)

						tempStatList.add(
							Stat(
								type = MyListMediaStatus.PLAN_TO_WATCH,
								value = data.userAnimeStatistics.numItemsPlanToWatch
							)
						)
					}

					_state.value = _state.value.copy(
						profileData = userData,
						animeStats = tempStatList,
						isLoading = false,
						error = ""
					)
				},
				onError = {
					_state.value = _state.value.copy(
						error = it ?: StringValue.StringResource(R.string.error_fetching)
							.toString(),
						isLoading = false
					)
				},
				onLoading = { _state.value = _state.value.copy(isLoading = true) }
			)
	}

	fun onEvent(event: ProfileEvent) {
		when (event) {
			is ProfileEvent.InitRequestChain -> {
				getUserProfile()
			}
		}
	}
}