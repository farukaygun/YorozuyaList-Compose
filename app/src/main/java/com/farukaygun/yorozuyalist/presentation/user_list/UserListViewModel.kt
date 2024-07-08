package com.farukaygun.yorozuyalist.presentation.user_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.use_case.AnimeUseCase
import com.farukaygun.yorozuyalist.domain.use_case.MangaUseCase
import com.farukaygun.yorozuyalist.util.ListType
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.StringValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class UserListViewModel(
	private val animeUseCase: AnimeUseCase,
	private val mangaUseCase: MangaUseCase
) : ViewModel() {
	private val _state = mutableStateOf(UserListState())
	val state = _state

	private var job: Job? = null

	private fun getUserList() {
		val getUserList = when (_state.value.type) {
			ListType.ANIME_LIST -> animeUseCase.executeUserAnimeList()
			ListType.MANGA_LIST -> mangaUseCase.executeUserMangaList()
		}

		job = getUserList
			.flowOn(Dispatchers.IO)
			.onEach {
				when (it) {
					is Resource.Success -> {
						_state.value = _state.value.copy(
							userList = it.data,
							isLoading = false,
							error = ""
						)
					}

					is Resource.Error -> {
						_state.value = _state.value.copy(
							error = it.message
								?: StringValue.StringResource(R.string.error_fetching).toString(),
						)
					}

					is Resource.Loading -> {
						_state.value = _state.value.copy(isLoading = true)
					}
				}
			}.launchIn(viewModelScope)
	}

	private fun loadMore() {
		job = _state.value.userList?.paging?.next?.let { nextPageUrl ->
			val loadMore = when (_state.value.type) {
				ListType.ANIME_LIST -> animeUseCase.executeUserAnimeList(url = nextPageUrl)
				ListType.MANGA_LIST -> mangaUseCase.executeUserMangaList(url = nextPageUrl)
			}

			loadMore
				.flowOn(Dispatchers.IO)
				.onEach {
					when (it) {
						is Resource.Success -> {
							val currentData = _state.value.userList?.data?.toMutableList()
							val newData = it.data?.data
							newData?.let { data -> currentData?.addAll(data) }

							_state.value = _state.value.copy(
								userList = it.data?.paging?.let { paging ->
									currentData?.let { data ->
										_state.value.userList?.copy(
											data = data,
											paging = paging
										)
									}
								},
								isLoadingMore = false,
								error = ""
							)
						}

						is Resource.Error -> {
							_state.value = _state.value.copy(
								error = it.message
									?: StringValue.StringResource(R.string.error_fetching)
										.toString(),
								isLoadingMore = false
							)
						}

						is Resource.Loading -> {
							_state.value = _state.value.copy(isLoadingMore = true)
						}
					}
				}.launchIn(viewModelScope)
		}
	}

	fun onEvent(event: UserListEvent) {
		when (event) {
			is UserListEvent.InitRequestChain -> getUserList()
			is UserListEvent.LoadMore -> loadMore()
		}

	}
}