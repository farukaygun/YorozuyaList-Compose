package com.farukaygun.yorozuyalist.domain.use_case.anime

import com.farukaygun.yorozuyalist.data.remote.dto.toMediaRanking
import com.farukaygun.yorozuyalist.domain.models.MediaRanking
import com.farukaygun.yorozuyalist.domain.repository.AnimeRepository
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.toAppError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAnimeRankingUseCase(private val repository: AnimeRepository) {
	operator fun invoke(
		rankingType: String,
		limit: Int = 20,
		offset: Int = 0
	): Flow<Resource<MediaRanking>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.getAnimeRanking(rankingType, limit, offset).toMediaRanking()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}

	operator fun invoke(url: String): Flow<Resource<MediaRanking>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.getAnimeRanking(url).toMediaRanking()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}
}
