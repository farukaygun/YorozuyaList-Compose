package com.farukaygun.yorozuyalist.presentation.search

import androidx.compose.runtime.mutableStateOf
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.use_case.AnimeUseCase
import com.farukaygun.yorozuyalist.presentation.base.BaseViewModel
import com.farukaygun.yorozuyalist.util.StringValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class SearchViewModel(
	private val animeUseCase: AnimeUseCase,
) : BaseViewModel<SearchState>() {
	override val _state = mutableStateOf(SearchState())

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

		jobs += animeUseCase.executeSearchedAnime(
			query = query,
			limit = 30
		)
			.flowOn(Dispatchers.IO)
			.handleResource(
				onSuccess = { animeSearched ->
					_state.value = _state.value.copy(
						animeSearched = animeSearched,
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

	private fun loadMore() {
		if (_state.value.query.isEmpty()) return

		_state.value.animeSearched?.paging?.next?.let { nextPageUrl ->
			jobs += animeUseCase.executeSearchedAnime(url = nextPageUrl)
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
							error = ""
						)
					},
					onError = { error ->
						_state.value = _state.value.copy(
							error = error
								?: StringValue.StringResource(R.string.error_fetching)
									.toString(),
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