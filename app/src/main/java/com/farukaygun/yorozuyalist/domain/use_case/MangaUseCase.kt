package com.farukaygun.yorozuyalist.domain.use_case

import com.farukaygun.yorozuyalist.data.remote.dto.manga.toMangaDetail
import com.farukaygun.yorozuyalist.data.remote.dto.manga.toMangaUserList
import com.farukaygun.yorozuyalist.data.remote.dto.toMediaRanking
import com.farukaygun.yorozuyalist.data.remote.dto.toMyListStatus
import com.farukaygun.yorozuyalist.data.repository.MangaRepository
import com.farukaygun.yorozuyalist.domain.models.MediaRanking
import com.farukaygun.yorozuyalist.domain.models.MyListStatus
import com.farukaygun.yorozuyalist.domain.models.manga.MangaDetail
import com.farukaygun.yorozuyalist.domain.models.manga.MangaUserList
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.enums.UserListSorting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MangaUseCase(
	private val repository: MangaRepository
) {
	fun executeUserMangaList(
		status: String?,
		sort: String = UserListSorting.MANGA_TITLE.value,
		limit: Int = 10,
		offset: Int = 0
	): Flow<Resource<MangaUserList>> = flow {
		try {
			emit(Resource.Loading())

			val userMangaList = repository.getUserMangaList(
				status = status,
				sort = sort,
				limit = limit,
				offset = offset
			)

			emit(Resource.Success(userMangaList.toMangaUserList()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}

	fun executeUserMangaList(
		url: String
	): Flow<Resource<MangaUserList>> = flow {
		try {
			emit(Resource.Loading())

			val userMangaList = repository.getUserMangaList(
				url = url
			)

			emit(Resource.Success(userMangaList.toMangaUserList()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}

	fun executeGetMangaDetail(
		id: String
	): Flow<Resource<MangaDetail>> = flow {
		try {
			emit(Resource.Loading())

			val mangaDetail = repository.getMangaDetail(
				id = id
			)

			emit(Resource.Success(mangaDetail.toMangaDetail()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}

	fun executeUpdateMyMangaListItem(
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

			val myListStatusItem = repository.updateMyMangaListItem(
				id = id,
				status = status,
				chapterCount = chapterCount,
				volumeCount = volumeCount,
				score = score,
				startDate = startDate,
				finishDate = finishDate,
				tags = tags,
				priority = priority,
				isRereading = isRereading,
				rereadCount = rereadCount,
				rereadValue = rereadValue,
				comments = comments
			)

			emit(Resource.Success(myListStatusItem.toMyListStatus()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}

	fun executeDeleteMyMangaListItem(
		id: Int
	): Flow<Resource<Boolean>> = flow {
		try {
			emit(Resource.Loading())

			val result = repository.deleteMyMangaListItem(
				id = id
			)

			emit(Resource.Success(result))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}

	fun executeGetMangaRanking(
		rankingType: String,
		limit: Int = 20,
		offset: Int = 0
	): Flow<Resource<MediaRanking>> = flow {
		try {
			emit(Resource.Loading())

			val mangaRanking = repository.getMangaRanking(
				rankingType = rankingType,
				limit = limit,
				offset = offset
			)

			emit(Resource.Success(mangaRanking.toMediaRanking()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}

	fun executeGetMangaRanking(
		url: String
	): Flow<Resource<MediaRanking>> = flow {
		try {
			emit(Resource.Loading())

			val mangaRanking = repository.getMangaRanking(
				url = url
			)

			emit(Resource.Success(mangaRanking.toMediaRanking()))
		} catch (e: Exception) {
			e.printStackTrace()
			emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
		}
	}
}