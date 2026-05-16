package com.farukaygun.yorozuyalist.domain.use_case.anime

import com.farukaygun.yorozuyalist.domain.repository.AnimeRepository
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.toAppError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteAnimeListItemUseCase(private val repository: AnimeRepository) {
	operator fun invoke(id: Int): Flow<Resource<Boolean>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.deleteMyAnimeListItem(id)))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}
}
