package com.farukaygun.yorozuyalist.domain.use_case.manga

import com.farukaygun.yorozuyalist.domain.repository.MangaRepository
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.toAppError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteMangaListItemUseCase(private val repository: MangaRepository) {
	operator fun invoke(id: Int): Flow<Resource<Boolean>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.deleteMyMangaListItem(id)))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}
}
