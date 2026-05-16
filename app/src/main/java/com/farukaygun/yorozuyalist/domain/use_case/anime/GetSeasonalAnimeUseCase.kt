package com.farukaygun.yorozuyalist.domain.use_case.anime

import com.farukaygun.yorozuyalist.data.remote.dto.anime.toAnimeSeasonal
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeSeasonal
import com.farukaygun.yorozuyalist.domain.repository.AnimeRepository
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.toAppError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSeasonalAnimeUseCase(private val repository: AnimeRepository) {
	operator fun invoke(
		year: Int,
		season: String,
		limit: Int = 20
	): Flow<Resource<AnimeSeasonal>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.getSeasonalAnime(year, season, limit).toAnimeSeasonal()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}

	operator fun invoke(url: String): Flow<Resource<AnimeSeasonal>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.getSeasonalAnime(url).toAnimeSeasonal()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}
}
