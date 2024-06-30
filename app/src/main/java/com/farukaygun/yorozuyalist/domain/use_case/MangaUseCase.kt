package com.farukaygun.yorozuyalist.domain.use_case

import com.farukaygun.yorozuyalist.data.remote.dto.manga.toMangaUserList
import com.farukaygun.yorozuyalist.data.repository.MangaRepository
import com.farukaygun.yorozuyalist.domain.model.MangaUserList
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.Sort
import com.farukaygun.yorozuyalist.util.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MangaUseCase(
	private val repository: MangaRepository
) {
	fun executeUserMangaList(
		status: String = Status.READING.value,
		sort: String = Sort.UPDATED_AT.value,
		limit: Int = 10,
		offset: Int = 0
	): Flow<Resource<MangaUserList>> = flow {
		try {
			emit(Resource.Loading())

			val userMangaList = repository.getUserMangaList(
				status = status,
				sort = sort,
				limit = limit,
				offset = offset
			)

			emit(Resource.Success(userMangaList.toMangaUserList()))
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	fun executeUserMangaList(
		url: String
	) : Flow<Resource<MangaUserList>> = flow {
		try {
			emit(Resource.Loading())

			val userMangaList = repository.getUserMangaList(
				url = url
			)

			emit(Resource.Success(userMangaList.toMangaUserList()))
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
}