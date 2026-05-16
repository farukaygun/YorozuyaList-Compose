package com.farukaygun.yorozuyalist.domain.use_case.manga

import com.farukaygun.yorozuyalist.data.remote.dto.toMyListStatus
import com.farukaygun.yorozuyalist.domain.models.MyListStatus
import com.farukaygun.yorozuyalist.domain.repository.MangaRepository
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.toAppError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateMangaListItemUseCase(private val repository: MangaRepository) {
	operator fun invoke(
		id: Int,
		status: String?,
		chapterCount: Int?,
		volumeCount: Int?,
		score: Int?,
		startDate: String?,
		finishDate: String?,
		tags: String?,
		priority: Int?,
		isRereading: Boolean?,
		rereadCount: Int?,
		rereadValue: Int?,
		comments: String?
	): Flow<Resource<MyListStatus>> = flow {
		try {
			emit(Resource.Loading())
			emit(Resource.Success(repository.updateMyMangaListItem(
				id, status, chapterCount, volumeCount, score, startDate, finishDate,
				tags, priority, isRereading, rereadCount, rereadValue, comments
			).toMyListStatus()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.toAppError()))
		}
	}
}
