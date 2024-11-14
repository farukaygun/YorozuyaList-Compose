package com.farukaygun.yorozuyalist.domain.use_case

import androidx.annotation.IntRange
import com.farukaygun.yorozuyalist.data.remote.dto.anime.toAnimeDetail
import com.farukaygun.yorozuyalist.data.remote.dto.anime.toAnimeSearched
import com.farukaygun.yorozuyalist.data.remote.dto.anime.toAnimeSeasonal
import com.farukaygun.yorozuyalist.data.remote.dto.anime.toAnimeSuggested
import com.farukaygun.yorozuyalist.data.remote.dto.anime.toAnimeUserList
import com.farukaygun.yorozuyalist.data.remote.dto.toMediaRanking
import com.farukaygun.yorozuyalist.data.remote.dto.toMyListStatus
import com.farukaygun.yorozuyalist.data.repository.AnimeRepository
import com.farukaygun.yorozuyalist.domain.models.MediaRanking
import com.farukaygun.yorozuyalist.domain.models.MyListStatus
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeDetail
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeSearched
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeSeasonal
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeSuggested
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeUserList
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AnimeUseCase(
	private val repository: AnimeRepository
) {
	fun executeSeasonalAnime(
		year: Int,
		season: String,
		limit: Int = 20
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
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}

	fun executeSeasonalAnime(
		url: String
	): Flow<Resource<AnimeSeasonal>> = flow {
		try {
			emit(Resource.Loading())

			val seasonalAnime = repository.getSeasonalAnime(
				url = url
			)

			emit(Resource.Success(seasonalAnime.toAnimeSeasonal()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}

	fun executeSuggestedAnime(
		limit: Int = 20,
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
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}

	fun executeSuggestedAnime(
		url: String
	): Flow<Resource<AnimeSuggested>> = flow {
		try {
			emit(Resource.Loading())

			val suggestedAnime = repository.getSuggestedAnime(
				url = url
			)

			emit(Resource.Success(suggestedAnime.toAnimeSuggested()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}

	fun executeSearchedAnime(
		query: String,
		limit: Int = 8,
		offset: Int = 0
	): Flow<Resource<AnimeSearched>> = flow {
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
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}

	fun executeSearchedAnime(
		url: String
	): Flow<Resource<AnimeSearched>> = flow {
		try {
			emit(Resource.Loading())

			val searchedAnime = repository.getSearchedAnime(
				url = url
			)

			emit(Resource.Success(searchedAnime.toAnimeSearched()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}

	fun executeUserAnimeList(
		status: String?,
		sort: String = Sort.ANIME_TITLE.format(),
		limit: Int = 10,
		offset: Int = 0
	): Flow<Resource<AnimeUserList>> = flow {
		try {
			emit(Resource.Loading())

			val userAnimeList = repository.getUserAnimeList(
				status = status,
				sort = sort,
				limit = limit,
				offset = offset
			)

			emit(Resource.Success(userAnimeList.toAnimeUserList()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}

	fun executeUserAnimeList(
		url: String
	): Flow<Resource<AnimeUserList>> = flow {
		try {
			emit(Resource.Loading())

			val userAnimeList = repository.getUserAnimeList(
				url = url
			)

			emit(Resource.Success(userAnimeList.toAnimeUserList()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}

	fun executeGetAnimeDetail(
		id: String
	) : Flow<Resource<AnimeDetail>> = flow {
		try {
			emit(Resource.Loading())

			val animeDetail = repository.getAnimeDetail(
				id = id
			)

			emit(Resource.Success(animeDetail.toAnimeDetail()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}

	fun executeUpdateMyAnimeListItem(
		id: Int,
		status: String?,
		episodeCount: Int?,
		@IntRange(0,10) score: Int?,
		startDate: String?,
		finishDate: String?,
		tags: String?,
		priority: Int?,
		isRewatching: Boolean?,
		@IntRange(0, 5) rewatchCount: Int?,
		@IntRange(0, 2) rewatchValue: Int?,
		comments: String?
	) : Flow<Resource<MyListStatus>> = flow {
		try {
			emit(Resource.Loading())

			val myListStatusItem = repository.updateMyAnimeListItem(
				id = id,
				status = status,
				episodeCount = episodeCount,
				score = score,
				startDate = startDate,
				finishDate = finishDate,
				tags = tags,
				priority = priority,
				isRewatching = isRewatching,
				rewatchCount = rewatchCount,
				rewatchValue = rewatchValue,
				comments = comments
			)

			emit(Resource.Success(myListStatusItem.toMyListStatus()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}

	fun executeDeleteMyAnimeListItem(
		id: Int
	) : Flow<Resource<Boolean>> = flow {
		try {
			emit(Resource.Loading())

			val result = repository.deleteMyAnimeListItem(
				id = id
			)

			emit(Resource.Success(result))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}

	fun executeGetAnimeRanking(
		rankingType: String,
		limit: Int = 20,
		offset: Int = 0
	): Flow<Resource<MediaRanking>> = flow {
		try {
			emit(Resource.Loading())

			val animeRanking = repository.getAnimeRanking(
				rankingType = rankingType,
				limit = limit,
				offset = offset
			)

			emit(Resource.Success(animeRanking.toMediaRanking()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}

	fun executeGetAnimeRanking(
		url: String
	): Flow<Resource<MediaRanking>> = flow {
		try {
			emit(Resource.Loading())

			val animeRanking = repository.getAnimeRanking(
				url = url
			)

			emit(Resource.Success(animeRanking.toMediaRanking()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}
}