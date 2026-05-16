package com.farukaygun.yorozuyalist.domain.use_case.anime

import com.farukaygun.yorozuyalist.data.remote.dto.anime.toAnimeSearched
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeSearched
import com.farukaygun.yorozuyalist.domain.repository.AnimeRepository
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.toAppError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchAnimeUseCase(private val repository: AnimeRepository) {
	operator fun invoke(
		query: String,
		limit: Int = 8,
		offset: Int = 0
	): Flow<Resource<AnimeSearched>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.getSearchedAnime(query, limit, offset).toAnimeSearched()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}

	operator fun invoke(url: String): Flow<Resource<AnimeSearched>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.getSearchedAnime(url).toAnimeSearched()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}
}
