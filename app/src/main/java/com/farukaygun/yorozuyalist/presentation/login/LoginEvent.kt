package com.farukaygun.yorozuyalist.presentation.login

import android.content.Context
import android.net.Uri

sealed class LoginEvent {
    data class Login(val context: Context) : LoginEvent()
}
