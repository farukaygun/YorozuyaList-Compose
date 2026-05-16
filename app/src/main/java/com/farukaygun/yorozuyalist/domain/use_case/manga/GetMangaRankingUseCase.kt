package com.farukaygun.yorozuyalist.domain.use_case.manga

import com.farukaygun.yorozuyalist.data.remote.dto.toMediaRanking
import com.farukaygun.yorozuyalist.domain.models.MediaRanking
import com.farukaygun.yorozuyalist.domain.repository.MangaRepository
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.toAppError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMangaRankingUseCase(private val repository: MangaRepository) {
	operator fun invoke(
		rankingType: String,
		limit: Int = 20,
		offset: Int = 0
	): Flow<Resource<MediaRanking>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.getMangaRanking(rankingType, limit, offset).toMediaRanking()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}

	operator fun invoke(url: String): Flow<Resource<MediaRanking>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.getMangaRanking(url).toMediaRanking()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}
}
