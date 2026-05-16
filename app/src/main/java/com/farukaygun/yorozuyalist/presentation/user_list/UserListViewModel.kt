package com.farukaygun.yorozuyalist.presentation.user_list

import androidx.lifecycle.SavedStateHandle
import com.farukaygun.yorozuyalist.domain.interfaces.MediaList
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeUserList
import com.farukaygun.yorozuyalist.domain.models.enums.MyListMediaStatus
import com.farukaygun.yorozuyalist.domain.use_case.anime.GetUserAnimeListUseCase
import com.farukaygun.yorozuyalist.domain.use_case.manga.GetUserMangaListUseCase
import com.farukaygun.yorozuyalist.presentation.base.BaseViewModel
import com.farukaygun.yorozuyalist.util.enums.ScreenType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn

class UserListViewModel(
	private val getUserAnimeList: GetUserAnimeListUseCase,
	private val getUserMangaList: GetUserMangaListUseCase,
	savedStateHandle: SavedStateHandle
) : BaseViewModel<UserListState>() {
	override val _state = MutableStateFlow(UserListState())

	init {
		savedStateHandle.get<String>("SCREEN_TYPE_PARAM")?.let { type ->
			_state.value = _state.value.copy(type = ScreenType.valueOf(type))
			onEvent(UserListEvent.InitRequestChain)
		}
	}

	private fun getUserList() {
		jobs += when (_state.value.type) {
			ScreenType.ANIME -> getUserAnimeList(_state.value.filter)
				.flowOn(Dispatchers.IO)
				.handleResource(
					onSuccess = { userList ->
						_state.value = _state.value.copy(userList = userList, isLoading = false, error = null)
					},
					onError = { error ->
						_state.value = _state.value.copy(error = error, isLoading = false)
					},
					onLoading = { _state.value = _state.value.copy(isLoading = true) }
				)
			ScreenType.MANGA -> getUserMangaList(_state.value.filter)
				.flowOn(Dispatchers.IO)
				.handleResource(
					onSuccess = { userList ->
						_state.value = _state.value.copy(userList = userList, isLoading = false, error = null)
					},
					onError = { error ->
						_state.value = _state.value.copy(error = error, isLoading = false)
					},
					onLoading = { _state.value = _state.value.copy(isLoading = true) }
				)
		}
	}

	private fun loadMore() {
		_state.value.userList?.paging?.next?.let { nextPageUrl ->
			fun mergeAndUpdate(userList: MediaList?) {
				val currentData = _state.value.userList?.data?.toMutableList() ?: mutableListOf()
				val newData = userList?.data ?: emptyList()
				val mergedData = (currentData + newData).distinctBy { media -> media.node.id }
				_state.value = _state.value.copy(
					userList = userList?.paging?.let { paging ->
						AnimeUserList(data = mergedData, paging = paging)
					},
					isLoadingMore = false,
					error = null
				)
			}

			jobs += when (_state.value.type) {
				ScreenType.ANIME -> getUserAnimeList(url = nextPageUrl)
					.flowOn(Dispatchers.IO)
					.handleResource(
						onSuccess = ::mergeAndUpdate,
						onError = { error ->
							_state.value = _state.value.copy(error = error, isLoadingMore = false)
						},
						onLoading = { _state.value = _state.value.copy(isLoadingMore = true) }
					)
				ScreenType.MANGA -> getUserMangaList(url = nextPageUrl)
					.flowOn(Dispatchers.IO)
					.handleResource(
						onSuccess = ::mergeAndUpdate,
						onError = { error ->
							_state.value = _state.value.copy(error = error, isLoadingMore = false)
						},
						onLoading = { _state.value = _state.value.copy(isLoadingMore = true) }
					)
			}
		}
	}

	private fun filterList(filter: MyListMediaStatus?) {
		_state.value = _state.value.copy(
			filter = filter?.apiName
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
