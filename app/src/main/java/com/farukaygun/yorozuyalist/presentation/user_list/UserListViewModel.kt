package com.farukaygun.yorozuyalist.presentation.user_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.interfaces.MediaList
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeUserList
import com.farukaygun.yorozuyalist.domain.models.enums.MyListMediaStatus
import com.farukaygun.yorozuyalist.domain.use_case.AnimeUseCase
import com.farukaygun.yorozuyalist.domain.use_case.MangaUseCase
import com.farukaygun.yorozuyalist.presentation.base.BaseViewModel
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.ScreenType
import com.farukaygun.yorozuyalist.util.StringValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class UserListViewModel(
	private val animeUseCase: AnimeUseCase,
	private val mangaUseCase: MangaUseCase,
	savedStateHandle: SavedStateHandle
) : BaseViewModel<UserListState>() {
	override val _state = mutableStateOf(UserListState())

	init {
		savedStateHandle.get<String>("SCREEN_TYPE_PARAM")?.let { type ->
			_state.value = _state.value.copy(type = ScreenType.valueOf(type))
			onEvent(UserListEvent.InitRequestChain)
		}
	}

	@Suppress("UNCHECKED_CAST")
	private fun getUserList() {
		val getUserList = when (_state.value.type) {
			ScreenType.ANIME -> animeUseCase.executeUserAnimeList(_state.value.filter)
			ScreenType.MANGA -> mangaUseCase.executeUserMangaList(_state.value.filter)
		} as Flow<Resource<MediaList>>

		jobs += getUserList
			.flowOn(Dispatchers.IO)
			.handleResource(
				onSuccess = { userList ->
					_state.value = _state.value.copy(
						userList = userList,
						isLoading = false,
						error = ""
					)
				},
				onError = { error ->
					_state.value = _state.value.copy(
						error = error
							?: StringValue.StringResource(R.string.error_fetching).toString(),
						isLoading = false
					)
				},
				onLoading = {
					_state.value = _state.value.copy(isLoading = true)
				}
			)
	}

	@Suppress("UNCHECKED_CAST")
	private fun loadMore() {
		_state.value.userList?.paging?.next?.let { nextPageUrl ->
			val loadMore = when (_state.value.type) {
				ScreenType.ANIME -> animeUseCase.executeUserAnimeList(url = nextPageUrl)
				ScreenType.MANGA -> mangaUseCase.executeUserMangaList(url = nextPageUrl)
			} as Flow<Resource<MediaList>>

			jobs += loadMore
				.flowOn(Dispatchers.IO)
				.handleResource(
					onSuccess = { userList ->
						val currentData =
							_state.value.userList?.data?.toMutableList() ?: mutableListOf()
						val newData = userList?.data ?: emptyList()
						val mergedData =
							(currentData + newData).distinctBy { media -> media.node.id }

						_state.value = _state.value.copy(
							userList = userList?.paging?.let { paging ->
								AnimeUserList(
									data = mergedData,
									paging = paging
								)
							},
							isLoadingMore = false,
							error = ""
						)
					},
					onError = { error ->
						_state.value = _state.value.copy(
							error = error
								?: StringValue.StringResource(R.string.error_fetching).toString(),
							isLoadingMore = false
						)
					},
					onLoading = {
						_state.value = _state.value.copy(isLoadingMore = true)
					}
				)
		}
	}

	private fun filterList(filter: MyListMediaStatus?) {
		_state.value = _state.value.copy(
			filter = filter?.formatForApi()
		)

		getUserList()
	}

	fun onEvent(event: UserListEvent) {
		when (event) {
			is UserListEvent.InitRequestChain -> getUserList()
			is UserListEvent.LoadMore -> loadMore()
			is UserListEvent.FilterList -> filterList(event.filter)
		}
	}
}