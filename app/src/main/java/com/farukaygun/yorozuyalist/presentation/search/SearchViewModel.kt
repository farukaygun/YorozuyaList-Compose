package com.farukaygun.yorozuyalist.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.model.Paging
import com.farukaygun.yorozuyalist.domain.model.anime.AnimeSearched
import com.farukaygun.yorozuyalist.domain.use_case.AnimeUseCase
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.StringValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SearchViewModel(
	private val animeUseCase: AnimeUseCase,
) : ViewModel() {
	private val _state = mutableStateOf(SearchState())
	val state: State<SearchState> = _state

	val scrollToTop = mutableStateOf(false)

	private var job: Job? = null

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

		job = animeUseCase.executeSearchedAnime(
			query = query,
			limit = 30
		)
			.flowOn(Dispatchers.IO)
			.onEach {
				when (it) {
					is Resource.Success -> {
						_state.value = _state.value.copy(
							animeSearched = it.data,
							isLoading = false,
							error = ""
						)
					}

					is Resource.Error -> {
						_state.value = _state.value.copy(
							error = it.message
								?: StringValue.StringResource(R.string.error_fetching).toString()
						)
					}

					is Resource.Loading -> {
						_state.value = _state.value.copy(isLoading = true)
					}
				}
			}.launchIn(viewModelScope)
	}

	private fun loadMore() {
		if (_state.value.isLoading ||
			_state.value.query.isEmpty()
		) return

		job = _state.value.animeSearched?.paging?.next?.let { nextPageUrl ->
			animeUseCase.executeSearchedAnime(url = nextPageUrl)
				.flowOn(Dispatchers.IO)
				.onEach {
					when (it) {
						is Resource.Success -> {
							val currentData = _state.value.animeSearched?.data?.toMutableList()
							val newData = it.data?.data
							newData?.let { data -> currentData?.addAll(data) }

							_state.value = _state.value.copy(
								animeSearched = it.data?.paging?.let { paging ->
									currentData?.let { data ->
										_state.value.animeSearched?.copy(
											data = data,
											paging = paging
										)
									}
								},
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