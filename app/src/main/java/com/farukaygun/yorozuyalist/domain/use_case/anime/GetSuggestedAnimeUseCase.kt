package com.farukaygun.yorozuyalist.domain.use_case.anime

import com.farukaygun.yorozuyalist.data.remote.dto.anime.toAnimeSuggested
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeSuggested
import com.farukaygun.yorozuyalist.domain.repository.AnimeRepository
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.toAppError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSuggestedAnimeUseCase(private val repository: AnimeRepository) {
	operator fun invoke(
		limit: Int = 20,
		offset: Int = 0
	): Flow<Resource<AnimeSuggested>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.getSuggestedAnime(limit, offset).toAnimeSuggested()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}

	operator fun invoke(url: String): Flow<Resource<AnimeSuggested>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.getSuggestedAnime(url).toAnimeSuggested()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}
}
