package com.farukaygun.yorozuyalist.data.di

import com.farukaygun.yorozuyalist.data.di.AppModule.provideKtorClient
import com.farukaygun.yorozuyalist.data.remote.APIService
import com.farukaygun.yorozuyalist.data.remote.APIServiceImpl
import com.farukaygun.yorozuyalist.data.repository.LoginRepository
import com.farukaygun.yorozuyalist.domain.repository.LoginRepositoryImpl
import com.farukaygun.yorozuyalist.domain.use_case.LoginUseCase
import com.farukaygun.yorozuyalist.presentation.home.HomeViewModel
import com.farukaygun.yorozuyalist.presentation.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val apiServiceModule = module {
	single { provideKtorClient() }
	single<APIService> { APIServiceImpl(get()) }
}

val viewModelModule = module {
	viewModel { LoginViewModel(get()) }
	viewModel { HomeViewModel(get()) }
}

val useCaseModule = module {
	single { LoginUseCase(get()) }
}

val repositoryModule = module {
	single<LoginRepository> { LoginRepositoryImpl(get()) }
}