package com.farukaygun.yorozuyalist.data.di

import com.farukaygun.yorozuyalist.data.di.AppModule.provideKtorClient
import com.farukaygun.yorozuyalist.data.remote.APIService
import com.farukaygun.yorozuyalist.data.remote.APIServiceImpl
import com.farukaygun.yorozuyalist.data.repository.AnimeRepository
import com.farukaygun.yorozuyalist.data.repository.LoginRepository
import com.farukaygun.yorozuyalist.data.repository.MangaRepository
import com.farukaygun.yorozuyalist.data.repository.UserRepository
import com.farukaygun.yorozuyalist.domain.repository.AnimeRepositoryImpl
import com.farukaygun.yorozuyalist.domain.repository.LoginRepositoryImpl
import com.farukaygun.yorozuyalist.domain.repository.MangaRepositoryImpl
import com.farukaygun.yorozuyalist.domain.repository.UserRepositoryImpl
import com.farukaygun.yorozuyalist.domain.use_case.AnimeUseCase
import com.farukaygun.yorozuyalist.domain.use_case.LoginUseCase
import com.farukaygun.yorozuyalist.domain.use_case.MangaUseCase
import com.farukaygun.yorozuyalist.domain.use_case.UserUseCase
import com.farukaygun.yorozuyalist.presentation.calendar.CalendarViewModel
import com.farukaygun.yorozuyalist.presentation.detail.DetailViewModel
import com.farukaygun.yorozuyalist.presentation.detail.bottom_sheet.MyListModalBottomSheetViewModel
import com.farukaygun.yorozuyalist.presentation.grid_list.GridListViewModel
import com.farukaygun.yorozuyalist.presentation.home.HomeViewModel
import com.farukaygun.yorozuyalist.presentation.login.LoginViewModel
import com.farukaygun.yorozuyalist.presentation.profile.ProfileViewModel
import com.farukaygun.yorozuyalist.presentation.search.SearchViewModel
import com.farukaygun.yorozuyalist.presentation.user_list.UserListViewModel
import com.farukaygun.yorozuyalist.util.SharedPrefsHelper
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val apiServiceModule = module {
	single { provideKtorClient() }
	single<APIService> { APIServiceImpl(get(), get()) }
	single { SharedPrefsHelper(get()) }
}

val viewModelModule = module {
	viewModel { LoginViewModel(get(), get()) }
	viewModel { HomeViewModel(get()) }
	viewModel { CalendarViewModel(get()) }
	viewModel { UserListViewModel(get(), get(), get()) }
	viewModel { ProfileViewModel(get()) }
	viewModel { SearchViewModel(get()) }
	viewModel { GridListViewModel(get(), get()) }
	viewModel { DetailViewModel(get(), get(), get()) }
	viewModel { MyListModalBottomSheetViewModel(get(), get()) }
}

val useCaseModule = module {
	single { LoginUseCase(get()) }
	single { AnimeUseCase(get()) }
	single { MangaUseCase(get()) }
	single { UserUseCase(get()) }
}

val repositoryModule = module {
	single<LoginRepository> { LoginRepositoryImpl(get()) }
	single<AnimeRepository> { AnimeRepositoryImpl(get()) }
	single<MangaRepository> { MangaRepositoryImpl(get()) }
	single<UserRepository> { UserRepositoryImpl(get()) }
}