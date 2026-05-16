package com.farukaygun.yorozuyalist.data.di

import com.farukaygun.yorozuyalist.data.di.AppModule.provideKtorClient
import com.farukaygun.yorozuyalist.data.remote.APIService
import com.farukaygun.yorozuyalist.data.remote.APIServiceImpl
import com.farukaygun.yorozuyalist.data.repository.AnimeRepositoryImpl
import com.farukaygun.yorozuyalist.data.repository.LoginRepositoryImpl
import com.farukaygun.yorozuyalist.data.repository.MangaRepositoryImpl
import com.farukaygun.yorozuyalist.data.repository.UserRepositoryImpl
import com.farukaygun.yorozuyalist.domain.repository.AnimeRepository
import com.farukaygun.yorozuyalist.domain.repository.LoginRepository
import com.farukaygun.yorozuyalist.domain.repository.MangaRepository
import com.farukaygun.yorozuyalist.domain.repository.UserRepository
import com.farukaygun.yorozuyalist.domain.use_case.anime.DeleteAnimeListItemUseCase
import com.farukaygun.yorozuyalist.domain.use_case.anime.GetAnimeDetailUseCase
import com.farukaygun.yorozuyalist.domain.use_case.anime.GetAnimeRankingUseCase
import com.farukaygun.yorozuyalist.domain.use_case.anime.GetSeasonalAnimeUseCase
import com.farukaygun.yorozuyalist.domain.use_case.anime.GetSuggestedAnimeUseCase
import com.farukaygun.yorozuyalist.domain.use_case.anime.GetUserAnimeListUseCase
import com.farukaygun.yorozuyalist.domain.use_case.anime.SearchAnimeUseCase
import com.farukaygun.yorozuyalist.domain.use_case.anime.UpdateAnimeListItemUseCase
import com.farukaygun.yorozuyalist.domain.use_case.login.GetAccessTokenUseCase
import com.farukaygun.yorozuyalist.domain.use_case.login.GetRefreshTokenUseCase
import com.farukaygun.yorozuyalist.domain.use_case.manga.DeleteMangaListItemUseCase
import com.farukaygun.yorozuyalist.domain.use_case.manga.GetMangaDetailUseCase
import com.farukaygun.yorozuyalist.domain.use_case.manga.GetMangaRankingUseCase
import com.farukaygun.yorozuyalist.domain.use_case.manga.GetUserMangaListUseCase
import com.farukaygun.yorozuyalist.domain.use_case.manga.UpdateMangaListItemUseCase
import com.farukaygun.yorozuyalist.domain.use_case.user.GetUserProfileUseCase
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
	single { SharedPrefsHelper(get()) }
	single { provideKtorClient(get()) }
	single<APIService> { APIServiceImpl(get()) }
}

val viewModelModule = module {
	viewModel { LoginViewModel(get(), get(), get()) }
	viewModel { HomeViewModel(get(), get()) }
	viewModel { CalendarViewModel(get()) }
	viewModel { UserListViewModel(get(), get(), get()) }
	viewModel { ProfileViewModel(get()) }
	viewModel { SearchViewModel(get()) }
	viewModel { GridListViewModel(get(), get(), get(), get()) }
	viewModel { DetailViewModel(get(), get(), get()) }
	viewModel { MyListModalBottomSheetViewModel(get(), get(), get(), get()) }
}

val useCaseModule = module {
	single { GetSeasonalAnimeUseCase(get()) }
	single { GetSuggestedAnimeUseCase(get()) }
	single { SearchAnimeUseCase(get()) }
	single { GetUserAnimeListUseCase(get()) }
	single { GetAnimeDetailUseCase(get()) }
	single { UpdateAnimeListItemUseCase(get()) }
	single { DeleteAnimeListItemUseCase(get()) }
	single { GetAnimeRankingUseCase(get()) }

	single { GetUserMangaListUseCase(get()) }
	single { GetMangaDetailUseCase(get()) }
	single { UpdateMangaListItemUseCase(get()) }
	single { DeleteMangaListItemUseCase(get()) }
	single { GetMangaRankingUseCase(get()) }

	single { GetAccessTokenUseCase(get()) }
	single { GetRefreshTokenUseCase(get()) }

	single { GetUserProfileUseCase(get()) }
}

val repositoryModule = module {
	single<LoginRepository> { LoginRepositoryImpl(get()) }
	single<AnimeRepository> { AnimeRepositoryImpl(get()) }
	single<MangaRepository> { MangaRepositoryImpl(get()) }
	single<UserRepository> { UserRepositoryImpl(get()) }
}
