package com.farukaygun.yorozuyalist.util

import io.ktor.client.plugins.ResponseException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class AppError {
	data object NetworkError : AppError()
	data object Unauthorized : AppError()
	data class ApiError(val code: Int) : AppError()
	data class UnknownError(val cause: String) : AppError()

	fun toMessage(): String = when (this) {
		is NetworkError -> "No internet connection."
		is Unauthorized -> "Your session has expired. Please log in again."
		is ApiError -> "Server error ($code)."
		is UnknownError -> cause
	}
}

fun Throwable.toAppError(): AppError = when (this) {
	is UnknownHostException,
	is ConnectException,
	is SocketTimeoutException -> AppError.NetworkError

	is ResponseException -> when (response.status.value) {
		401 -> AppError.Unauthorized
		else -> AppError.ApiError(response.status.value)
	}

	else -> AppError.UnknownError(localizedMessage ?: "An unexpected error occurred.")
}
