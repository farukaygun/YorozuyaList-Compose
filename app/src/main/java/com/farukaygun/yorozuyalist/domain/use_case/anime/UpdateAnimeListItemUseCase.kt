package com.farukaygun.yorozuyalist.domain.use_case.anime

import androidx.annotation.IntRange
import com.farukaygun.yorozuyalist.data.remote.dto.toMyListStatus
import com.farukaygun.yorozuyalist.domain.models.MyListStatus
import com.farukaygun.yorozuyalist.domain.repository.AnimeRepository
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.toAppError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateAnimeListItemUseCase(private val repository: AnimeRepository) {
	operator fun invoke(
		id: Int,
		status: String?,
		episodeCount: Int?,
		@IntRange(0, 10) score: Int?,
		startDate: String?,
		finishDate: String?,
		tags: String?,
		priority: Int?,
		isRewatching: Boolean?,
		@IntRange(0, 5) rewatchCount: Int?,
		@IntRange(0, 2) rewatchValue: Int?,
		comments: String?
	): Flow<Resource<MyListStatus>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.updateMyAnimeListItem(
				id, status, episodeCount, score, startDate, finishDate,
				tags, priority, isRewatching, rewatchCount, rewatchValue, comments
			).toMyListStatus()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}
}
