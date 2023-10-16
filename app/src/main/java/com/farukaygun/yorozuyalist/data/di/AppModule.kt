package com.farukaygun.yorozuyalist.data.di

import com.farukaygun.yorozuyalist.data.remote.APIService
import com.farukaygun.yorozuyalist.data.remote.APIServiceImpl
import com.farukaygun.yorozuyalist.data.repository.LoginRepository
import com.farukaygun.yorozuyalist.domain.repository.LoginRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
	@Provides
	@Singleton
	fun provideKtorClient(): HttpClient {
		val client = HttpClient(Android) {
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
				level = LogLevel.ALL
			}
		}
		return client
	}

	@Provides
	@Singleton
	fun provideAPIService(client: HttpClient): APIService {
		return APIServiceImpl(client)
	}

	@Provides
	@Singleton
	fun provideLoginRepository(api: APIService): LoginRepository {
		return LoginRepositoryImpl(api)
	}
}