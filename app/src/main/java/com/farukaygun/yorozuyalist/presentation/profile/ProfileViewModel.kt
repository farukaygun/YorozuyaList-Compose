package com.farukaygun.yorozuyalist.presentation.profile

import com.farukaygun.yorozuyalist.domain.models.Stat
import com.farukaygun.yorozuyalist.domain.models.enums.MyListMediaStatus
import com.farukaygun.yorozuyalist.domain.use_case.user.GetUserProfileUseCase
import com.farukaygun.yorozuyalist.presentation.base.BaseViewModel
import com.farukaygun.yorozuyalist.util.Constants
import com.farukaygun.yorozuyalist.util.Extensions.CustomExtensions.openCustomTab
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn

class ProfileViewModel(
	private val getUserProfile: GetUserProfileUseCase
) : BaseViewModel<ProfileState>() {
	override val _state = MutableStateFlow(ProfileState())

	init {
		onEvent(ProfileEvent.InitRequestChain)
	}

	private fun loadUserProfile() {
		jobs += getUserProfile()
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

	fun onEvent(event: ProfileEvent) {
		when (event) {
			is ProfileEvent.InitRequestChain -> {
				loadUserProfile()
			}

			is ProfileEvent.OpenProfileWithCustomTab -> {
				event.context.openCustomTab("${Constants.HOME_URL}/profile/${event.username}")
			}
		}
	}
}
