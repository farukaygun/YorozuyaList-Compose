package com.farukaygun.yorozuyalist.data.remote

import com.farukaygun.yorozuyalist.data.remote.dto.AccessTokenDto
import com.farukaygun.yorozuyalist.data.remote.dto.MediaRankingDto
import com.farukaygun.yorozuyalist.data.remote.dto.MyListStatusDto
import com.farukaygun.yorozuyalist.data.remote.dto.RefreshTokenDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeDetailDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeSearchedDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeSeasonalDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeSuggestedDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.AnimeUserListDto
import com.farukaygun.yorozuyalist.data.remote.dto.manga.MangaDetailDto
import com.farukaygun.yorozuyalist.data.remote.dto.manga.MangaUserListDto
import com.farukaygun.yorozuyalist.data.remote.dto.user.UserDto

interface APIService {
	suspend fun getAuthToken(
		code: String,
		clientId: String,
		codeVerifier: String
	): AccessTokenDto

	suspend fun getRefreshToken(
		grantType: String,
		refreshToken: String
	): RefreshTokenDto

	suspend fun getSeasonalAnime(
		year: Int,
		season: String,
		limit: Int
	): AnimeSeasonalDto

	suspend fun getSeasonalAnime(
		url: String
	): AnimeSeasonalDto

	suspend fun getSuggestedAnime(
		limit: Int,
		offset: Int,
	): AnimeSuggestedDto

	suspend fun getSuggestedAnime(
		url: String
	): AnimeSuggestedDto

	suspend fun getSearchedAnime(
		query: String,
		limit: Int,
		offset: Int
	): AnimeSearchedDto

	suspend fun getSearchedAnime(
		url: String
	): AnimeSearchedDto

	suspend fun getUserAnimeList(
		status: String?,
		sort: String,
		limit: Int,
		offset: Int
	): AnimeUserListDto

	suspend fun getUserAnimeList(
		url: String
	): AnimeUserListDto

	suspend fun getUserMangaList(
		status: String?,
		sort: String,
		limit: Int,
		offset: Int
	): MangaUserListDto

	suspend fun getUserMangaList(
		url: String
	): MangaUserListDto

	suspend fun getUserProfile(): UserDto

	suspend fun getAnimeDetail(
		id: String
	): AnimeDetailDto

	suspend fun getMangaDetail(
		id: String
	) : MangaDetailDto

	suspend fun updateMyAnimeListItem(
		id: Int,
		status: String?,
		episodeCount: Int?,
		score: Int?,
		startDate: String?,
		finishDate: String?,
		tags: String?,
		priority: Int?,
		isRewatching: Boolean?,
		rewatchCount: Int?,
		rewatchValue: Int?,
		comments: String?
	) : MyListStatusDto

	suspend fun updateMyMangaListItem(
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
	) : MyListStatusDto

	suspend fun deleteMyAnimeListItem(
		id: Int
	): Boolean

	suspend fun deleteMyMangaListItem(
		id: Int
	): Boolean

	suspend fun getAnimeRanking(
		rankingType: String,
		limit: Int,
		offset: Int,
	) : MediaRankingDto

	suspend fun getAnimeRanking(
		url: String
	) : MediaRankingDto

	suspend fun getMangaRanking(
		rankingType: String,
		limit: Int,
		offset: Int,
	) : MediaRankingDto

	suspend fun getMangaRanking(
		url: String
	) : MediaRankingDto
}