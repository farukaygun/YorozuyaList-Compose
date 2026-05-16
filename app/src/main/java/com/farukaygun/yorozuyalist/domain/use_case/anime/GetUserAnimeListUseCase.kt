package com.farukaygun.yorozuyalist.domain.use_case.anime

import com.farukaygun.yorozuyalist.data.remote.dto.anime.toAnimeUserList
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeUserList
import com.farukaygun.yorozuyalist.domain.repository.AnimeRepository
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.enums.UserListSorting
import com.farukaygun.yorozuyalist.util.toAppError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserAnimeListUseCase(private val repository: AnimeRepository) {
	operator fun invoke(
		status: String?,
		sort: String = UserListSorting.ANIME_TITLE.value,
		limit: Int = 10,
		offset: Int = 0
	): Flow<Resource<AnimeUserList>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.getUserAnimeList(status, sort, limit, offset).toAnimeUserList()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}

	operator fun invoke(url: String): Flow<Resource<AnimeUserList>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.getUserAnimeList(url).toAnimeUserList()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}
}
