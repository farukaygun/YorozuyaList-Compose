package com.farukaygun.yorozuyalist.domain.use_case

import com.farukaygun.yorozuyalist.data.remote.dto.toAnimeSeasonal
import com.farukaygun.yorozuyalist.data.repository.AnimeRepository
import com.farukaygun.yorozuyalist.domain.model.anime.AnimeSeasonal
import com.farukaygun.yorozuyalist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AnimeUseCase(private val repository: AnimeRepository) {
	fun executeSeasonalAnime(
		year: Int,
		season: String,
		limit: Int
	): Flow<Resource<AnimeSeasonal>> = flow {
		try {
			emit(Resource.Loading())

			val seasonalAnime = repository.getSeasonalAnime(
				year = year,
				season = season,
				limit = limit
			)

			emit(Resource.Success(seasonalAnime.toAnimeSeasonal()))
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
}