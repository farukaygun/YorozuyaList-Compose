package com.farukaygun.yorozuyalist.data.di

import com.farukaygun.yorozuyalist.BuildConfig
import com.farukaygun.yorozuyalist.data.remote.dto.RefreshTokenDto
import com.farukaygun.yorozuyalist.util.Constants
import com.farukaygun.yorozuyalist.util.PrefKeys
import com.farukaygun.yorozuyalist.util.Private
import com.farukaygun.yorozuyalist.util.SharedPrefsHelper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.basicAuth
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson

object AppModule {
	fun provideKtorClient(sharedPrefsHelper: SharedPrefsHelper): HttpClient {
		return HttpClient(Android) {
			expectSuccess = true
			defaultRequest {
				contentType(ContentType.Application.Json)
				accept(ContentType.Application.Json)
			}

			install(ContentNegotiation) {
				gson {
					setPrettyPrinting()
					serializeNulls()
				}
			}

			install(Logging) {
				level = if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE
			}

			install(Auth) {
				bearer {
					// Yalnızca MAL API isteklerine token ekle; OAuth endpoint'lerine ekleme
					sendWithoutRequest { request ->
						request.url.host == "api.myanimelist.net"
					}

					loadTokens {
						val accessToken = sharedPrefsHelper.getString(PrefKeys.ACCESS_TOKEN)
						if (accessToken == "null" || accessToken.isBlank()) return@loadTokens null
						BearerTokens(
							accessToken = accessToken,
							refreshToken = sharedPrefsHelper.getString(PrefKeys.REFRESH_TOKEN)
						)
					}

					refreshTokens {
						val currentRefreshToken = oldTokens?.refreshToken
							?.takeIf { it != "null" && it.isNotBlank() }
							?: return@refreshTokens null

						val response: RefreshTokenDto = client.post("${Constants.OAUTH2_URL}token") {
							markAsRefreshTokenRequest()
							basicAuth(Private.CLIENT_ID, "")
							setBody(FormDataContent(Parameters.build {
								append("grant_type", "refresh_token")
								append("refresh_token", currentRefreshToken)
							}))
						}.body()

						sharedPrefsHelper.saveString(PrefKeys.ACCESS_TOKEN, response.accessToken)
						sharedPrefsHelper.saveString(PrefKeys.REFRESH_TOKEN, response.refreshToken)
						sharedPrefsHelper.saveLong(
							PrefKeys.EXPIRES_IN,
							System.currentTimeMillis() + response.expiresIn * 1000
						)

						BearerTokens(
							accessToken = response.accessToken,
							refreshToken = response.refreshToken
						)
					}
				}
			}
		}
	}
}
