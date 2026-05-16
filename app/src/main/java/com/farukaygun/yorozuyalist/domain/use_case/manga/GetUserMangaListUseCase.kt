package com.farukaygun.yorozuyalist.domain.use_case.manga

import com.farukaygun.yorozuyalist.data.remote.dto.manga.toMangaUserList
import com.farukaygun.yorozuyalist.domain.models.manga.MangaUserList
import com.farukaygun.yorozuyalist.domain.repository.MangaRepository
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.enums.UserListSorting
import com.farukaygun.yorozuyalist.util.toAppError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserMangaListUseCase(private val repository: MangaRepository) {
	operator fun invoke(
		status: String?,
		sort: String = UserListSorting.MANGA_TITLE.value,
		limit: Int = 10,
		offset: Int = 0
	): Flow<Resource<MangaUserList>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.getUserMangaList(status, sort, limit, offset).toMangaUserList()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}

	operator fun invoke(url: String): Flow<Resource<MangaUserList>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.getUserMangaList(url).toMangaUserList()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}
}
