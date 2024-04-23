package com.farukaygun.yorozuyalist.data.remote

import com.farukaygun.yorozuyalist.data.remote.dto.AccessTokenDto
import com.farukaygun.yorozuyalist.data.remote.dto.AnimeSeasonalDto
import com.farukaygun.yorozuyalist.data.remote.dto.RefreshTokenDto
import com.farukaygun.yorozuyalist.util.Constants
import com.farukaygun.yorozuyalist.util.Private
import com.farukaygun.yorozuyalist.util.SharedPrefsHelper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.basicAuth
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters

class APIServiceImpl(
	private val client: HttpClient,
	private val sharedPrefsHelper: SharedPrefsHelper
) : APIService {

	override suspend fun getAuthToken(
		code: String,
		clientId: String,
		codeVerifier: String,
	): AccessTokenDto {
		return client.post("${Constants.OAUTH2_URL}token") {
			setBody(FormDataContent(Parameters.build {
				append("client_id", clientId)
				append("code", code)
				append("code_verifier", codeVerifier)
				append("grant_type", "authorization_code")
			}))
		}.body()
	}

	override suspend fun getRefreshToken(
		grantType: String,
		refreshToken: String
	): RefreshTokenDto {
		return client.post("${Constants.OAUTH2_URL}token") {
			basicAuth(Private.CLIENT_ID, "")
			header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())

			setBody(FormDataContent(Parameters.build {
				append("grant_type", "refresh_token")
				append("refresh_token", refreshToken)
			}))
		}.body()
	}

	override suspend fun getSeasonalAnime(
		year: Int,
		season: String,
		limit: Int
	): AnimeSeasonalDto {
		return client.get("${Constants.BASE_URL}/anime/season/$year/$season") {
			header(HttpHeaders.Authorization, "Bearer ${sharedPrefsHelper.getString("accessToken")}")
			parameter("limit", limit)
		}.body()
	}
}