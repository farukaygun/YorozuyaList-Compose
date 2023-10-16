package com.farukaygun.yorozuyalist.presentation.login

import android.content.Context

sealed class LoginEvent {
	data class Login(val context: Context) : LoginEvent()
}
