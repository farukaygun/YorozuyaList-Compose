package com.farukaygun.yorozuyalist.domain.use_case

import com.farukaygun.yorozuyalist.data.remote.dto.toAnimeSearched
import com.farukaygun.yorozuyalist.data.remote.dto.toAnimeSeasonal
import com.farukaygun.yorozuyalist.data.remote.dto.toAnimeSuggested
import com.farukaygun.yorozuyalist.data.repository.AnimeRepository
import com.farukaygun.yorozuyalist.domain.model.anime.AnimeSearched
import com.farukaygun.yorozuyalist.domain.model.anime.AnimeSeasonal
import com.farukaygun.yorozuyalist.domain.model.anime.AnimeSuggested
import com.farukaygun.yorozuyalist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AnimeUseCase(private val repository: AnimeRepository) {
	fun executeSeasonalAnime(
		year: Int,
		season: String,
		limit: Int = 10
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

	fun executeSuggestedAnime(
		limit: Int = 10,
		offset: Int = 0
	): Flow<Resource<AnimeSuggested>> = flow {
		try {
			emit(Resource.Loading())

			val suggestedAnime = repository.getSuggestedAnime(
				limit = limit,
				offset = offset
			)

			emit(Resource.Success(suggestedAnime.toAnimeSuggested()))
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	fun executeSearchedAnime(
		query: String,
		limit: Int = 8,
		offset: Int = 0
	) : Flow<Resource<AnimeSearched>> = flow {
		try {
			emit(Resource.Loading())

			val searchedAnime = repository.getSearchedAnime(
				query = query,
				limit = limit,
				offset = offset
			)

			emit(Resource.Success(searchedAnime.toAnimeSearched()))
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	fun executeSearchedAnime(
		url: String
	) : Flow<Resource<AnimeSearched>> = flow {
		println("usecase executeSearchedAnime url: $url")
		try {
			emit(Resource.Loading())

			val searchedAnime = repository.getSearchedAnime(
				url = url
			)

			emit(Resource.Success(searchedAnime.toAnimeSearched()))
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
}