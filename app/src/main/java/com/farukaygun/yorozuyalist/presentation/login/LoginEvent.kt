package com.farukaygun.yorozuyalist.presentation.login

import android.content.Context
import android.content.Intent
import com.farukaygun.yorozuyalist.domain.models.AccessToken

sealed class LoginEvent {
	data class Login(val context: Context) : LoginEvent()
	data class ParseIntentData(val context: Context, val intent: Intent) : LoginEvent()
	data class SaveToken(val accesToken: AccessToken) : LoginEvent()
}
