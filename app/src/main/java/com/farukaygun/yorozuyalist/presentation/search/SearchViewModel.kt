package com.farukaygun.yorozuyalist.presentation.search

import androidx.compose.runtime.mutableStateOf
import com.farukaygun.yorozuyalist.domain.use_case.anime.SearchAnimeUseCase
import com.farukaygun.yorozuyalist.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn

class SearchViewModel(
	private val searchAnime: SearchAnimeUseCase,
) : BaseViewModel<SearchState>() {
	override val _state = MutableStateFlow(SearchState())

	val scrollToTop = mutableStateOf(false)

	private fun search(query: String) {
		if (query.isEmpty() ||
			_state.value.isLoading ||
			_state.value.query == query
		) return

		scrollToTop.value = true
		_state.value = _state.value.copy(
			query = query,
			animeSearched = null
		)

		jobs += searchAnime(
			query = query,
			limit = 30
		)
			.flowOn(Dispatchers.IO)
			.handleResource(
				onSuccess = { animeSearched ->
					_state.value = _state.value.copy(
						animeSearched = animeSearched,
						isLoading = false,
						error = null
					)
				},
				onError = { error ->
					_state.value = _state.value.copy(
						error = error,
						isLoading = false
					)
				},
				onLoading = {
					_state.value = _state.value.copy(isLoading = true)
				}
			)
	}

	private fun loadMore() {
		if (_state.value.query.isEmpty()) return

		_state.value.animeSearched?.paging?.next?.let { nextPageUrl ->
			jobs += searchAnime(url = nextPageUrl)
				.flowOn(Dispatchers.IO)
				.handleResource(
					onSuccess = { animeSearched ->
						val currentData = _state.value.animeSearched?.data?.toMutableList()
						val newData = animeSearched?.data
						newData?.let { data -> currentData?.addAll(data) }

						_state.value = _state.value.copy(
							animeSearched = animeSearched?.paging?.let { paging ->
								currentData?.let { data ->
									_state.value.animeSearched?.copy(
										data = data,
										paging = paging
									)
								}
							},
							isLoadingMore = false,
							error = null
						)
					},
					onError = { error ->
						_state.value = _state.value.copy(
							error = error,
							isLoadingMore = false
						)
					},
					onLoading = {
						_state.value = _state.value.copy(isLoadingMore = true)
					}
				)
		}
	}

	fun onEvent(event: SearchEvent) {
		when (event) {
			is SearchEvent.Search -> {
				search(event.query)
			}

			is SearchEvent.LoadMore -> {
				loadMore()
			}
		}
	}
}
