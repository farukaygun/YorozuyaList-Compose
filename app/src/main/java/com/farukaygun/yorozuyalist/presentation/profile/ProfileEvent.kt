package com.farukaygun.yorozuyalist.presentation.profile

import android.content.Context

sealed class ProfileEvent {
	data object InitRequestChain : ProfileEvent()
	data class OpenProfileWithCustomTab(val context: Context, val username: String) : ProfileEvent()
}