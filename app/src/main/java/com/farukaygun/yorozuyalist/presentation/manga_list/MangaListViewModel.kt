package com.farukaygun.yorozuyalist.presentation.manga_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.use_case.MangaUseCase
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.StringValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MangaListViewModel(
	private val mangaUseCase: MangaUseCase
) : ViewModel() {
	private val _state = mutableStateOf(MangaListState())
	val state: State<MangaListState> = _state

	var job: Job? = null

	private fun getUserMangaList() {
		job = mangaUseCase.executeUserMangaList()
			.flowOn(Dispatchers.IO)
			.onEach {
				when(it) {
					is Resource.Success -> {
						_state.value = _state.value.copy(
							userMangaList = it.data,
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

	private fun loadMore() {
		job = _state.value.userMangaList?.paging?.next?.let { nextPageUrl ->
			mangaUseCase.executeUserMangaList(url = nextPageUrl)
				.flowOn(Dispatchers.IO)
				.onEach {
					when (it) {
						is Resource.Success -> {
							val currentData = _state.value.userMangaList?.data?.toMutableList()
							val newData = it.data?.data
							newData?.let { data -> currentData?.addAll(data) }

							_state.value = _state.value.copy(
								userMangaList = it.data?.paging?.let { paging ->
									currentData?.let { data ->
										_state.value.userMangaList?.copy(
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

	fun onEvent(event: MangaListEvent) {
		when(event) {
			is MangaListEvent.InitRequestChain -> {
				getUserMangaList()
			}

			is MangaListEvent.LoadMore -> {
				loadMore()
			}
		}
	}
}