package com.farukaygun.yorozuyalist.presentation.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukaygun.yorozuyalist.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseViewModel<T> : ViewModel() {
	protected abstract val _state: MutableState<T>
	val state: State<T> by lazy { _state }

	protected val jobs = mutableListOf<Job>()

	protected inline fun <T> Flow<Resource<T>>.handleResource(
		crossinline onSuccess: (T?) -> Unit,
		crossinline onError: (String?) -> Unit = { _ -> },
		crossinline onLoading: () -> Unit = { }
	) : Job {
		return onEach {
			when (it) {
				is Resource.Success -> onSuccess(it.data)
				is Resource.Error -> onError(it.message)
				is Resource.Loading -> onLoading()
			}
		}.launchIn(viewModelScope)
	}
}