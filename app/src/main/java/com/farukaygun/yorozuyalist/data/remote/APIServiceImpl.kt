package com.farukaygun.yorozuyalist.data.remote

import com.farukaygun.yorozuyalist.data.remote.dto.AuthTokenDto
import com.farukaygun.yorozuyalist.util.Constants
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.request.forms.FormDataContent
import io.ktor.http.Parameters
import javax.inject.Inject

class APIServiceImpl @Inject constructor(private val client: HttpClient) : APIService {
	override suspend fun getAuthToken(
		code: String,
		clientId: String,
		codeVerifier: String,
	): AuthTokenDto {
		return client.post("${Constants.OAUTH2_URL}token") {
			setBody(FormDataContent(Parameters.build {
				append("client_id", clientId)
				append("code", code)
				append("code_verifier", codeVerifier)
				append("grant_type", "authorization_code")
			}))
		}.body()
	}
}