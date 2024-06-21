package com.farukaygun.yorozuyalist.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.model.Paging
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

	private var job: Job? = null

	private fun search(query: String) {
		job = animeUseCase.executeSearchedAnime(query = query)
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
							error = it.message ?: StringValue.StringResource(R.string.error_fetching).toString()
						)
					}
					is Resource.Loading -> {
						_state.value = _state.value.copy(isLoading = true)
					}
				}
			}.launchIn(viewModelScope)
	}

	private fun loadMore() {
		viewModelScope.launch(Dispatchers.IO) {
			_state.value.animeSearched?.paging?.next?.let { next ->
				animeUseCase.executeSearchedAnime(url = next)
					.onEach {
						when (it) {
							is Resource.Success -> {
								val currentData = _state.value.animeSearched?.data?.toMutableList() ?: mutableListOf()
								val newData = it.data?.data ?: emptyList()
								currentData.addAll(newData)

								_state.value = _state.value.copy(
									animeSearched = _state.value.animeSearched?.copy(
										data = currentData,
										paging = it.data?.paging ?: Paging(null, null)
									),
									isLoading = false,
									error = ""
								)
							}

							is Resource.Error -> {
								_state.value = _state.value.copy(
									error = it.message ?: StringValue.StringResource(R.string.error_fetching).toString()
								)
							}

							is Resource.Loading -> {
								_state.value = _state.value.copy(isLoading = true)
							}
						}
					}.collect()
			}
		}
	}

	fun onEvent(event: SearchEvent) {
		when (event) {
			is SearchEvent.Search -> { search(event.query) }
			is SearchEvent.LoadMore -> { loadMore() }
		}
	}
}

//		if (_state.value.isLoading || _state.value.animeSearched?.paging?.next.isNullOrEmpty()) return
//		println("url: ${_state.value.animeSearched?.paging?.next}")
//		animeUseCase.executeSearchedAnime(url = _state.value.animeSearched?.paging?.next!!)
//			.flowOn(Dispatchers.IO)
//			.onEach {
//				when (it) {
//					is Resource.Success -> {
//						val currentData = _state.value.animeSearched?.data?.toMutableList() ?: mutableListOf()
//						val newData = it.data?.data ?: emptyList()
//						currentData.addAll(newData)
//
//						_state.value = _state.value.copy(
//							animeSearched = _state.value.animeSearched?.copy(
//								data = currentData,
//								paging = it.data?.paging ?: Paging(null, null)
//							),
//							isLoading = false,
//							error = ""
//						)
//					}
//					is Resource.Error -> {
//						_state.value = _state.value.copy(
//							error = it.message ?: StringValue.StringResource(R.string.error_fetching).toString()
//						)
//					}
//					is Resource.Loading -> {
//						_state.value = _state.value.copy(isLoading = true)
//					}
//				}
//			}.launchIn(viewModelScope)