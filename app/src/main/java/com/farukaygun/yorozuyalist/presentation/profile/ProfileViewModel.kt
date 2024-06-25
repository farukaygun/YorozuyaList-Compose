package com.farukaygun.yorozuyalist.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ProfileViewModel(
) : ViewModel() {
	private val _state = mutableStateOf(ProfileState())
	val state: State<ProfileState> = _state
}