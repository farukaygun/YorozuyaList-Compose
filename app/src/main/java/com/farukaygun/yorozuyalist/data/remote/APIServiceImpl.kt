package com.farukaygun.yorozuyalist.data.remote

import com.farukaygun.yorozuyalist.data.remote.dto.AuthTokenDto
import com.farukaygun.yorozuyalist.util.Constants
import com.farukaygun.yorozuyalist.util.Private
import com.farukaygun.yorozuyalist.util.Util
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.request.forms.FormDataContent
import io.ktor.http.Parameters
import javax.inject.Inject

class APIServiceImpl @Inject constructor(private val client: HttpClient) : APIService {
	override suspend fun getAuthToken(): AuthTokenDto {
		val response = client.post(Constants.AUTH_URL) {
			FormDataContent(Parameters.build {
				append("response_type", "code")
				append("client_id", Private.CLIENT_ID)
				append("state", "auth")
				append("code_challenge", Util.generateCodeChallenge())
			})
		}
		return response.body()
	}
}