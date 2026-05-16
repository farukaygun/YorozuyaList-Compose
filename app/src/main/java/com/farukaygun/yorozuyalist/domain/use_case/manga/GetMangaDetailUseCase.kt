package com.farukaygun.yorozuyalist.domain.use_case.manga

import com.farukaygun.yorozuyalist.data.remote.dto.manga.toMangaDetail
import com.farukaygun.yorozuyalist.domain.models.manga.MangaDetail
import com.farukaygun.yorozuyalist.domain.repository.MangaRepository
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.toAppError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMangaDetailUseCase(private val repository: MangaRepository) {
	operator fun invoke(id: String): Flow<Resource<MangaDetail>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.getMangaDetail(id).toMangaDetail()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}
}
