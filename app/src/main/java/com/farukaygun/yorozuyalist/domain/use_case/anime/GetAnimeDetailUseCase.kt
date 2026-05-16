package com.farukaygun.yorozuyalist.domain.use_case.anime

import com.farukaygun.yorozuyalist.data.remote.dto.anime.toAnimeDetail
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeDetail
import com.farukaygun.yorozuyalist.domain.repository.AnimeRepository
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.toAppError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAnimeDetailUseCase(private val repository: AnimeRepository) {
	operator fun invoke(id: String): Flow<Resource<AnimeDetail>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.getAnimeDetail(id).toAnimeDetail()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}
}
