package com.farukaygun.yorozuyalist.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukaygun.yorozuyalist.util.AppError
import com.farukaygun.yorozuyalist.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseViewModel<T> : ViewModel() {
	protected abstract val _state: MutableStateFlow<T>
	val state: StateFlow<T> by lazy { _state.asStateFlow() }

	protected val jobs = mutableListOf<Job>()

	protected inline fun <T> Flow<Resource<T>>.handleResource(
		crossinline onSuccess: (T?) -> Unit,
		crossinline onError: (AppError) -> Unit = { _ -> },
		crossinline onLoading: () -> Unit = { }
	): Job {
		return onEach {
			when (it) {
				is Resource.Success -> onSuccess(it.data)
				is Resource.Error -> onError(it.error)
				is Resource.Loading -> onLoading()
			}
		}.launchIn(viewModelScope)
	}
}
