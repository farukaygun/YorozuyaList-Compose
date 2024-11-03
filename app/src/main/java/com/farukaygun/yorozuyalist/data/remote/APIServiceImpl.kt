package com.farukaygun.yorozuyalist.data.remote

import androidx.annotation.IntRange
import com.farukaygun.yorozuyalist.data.remote.dto.AccessTokenDto
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
import com.farukaygun.yorozuyalist.util.Constants
import com.farukaygun.yorozuyalist.util.Private
import com.farukaygun.yorozuyalist.util.SharedPrefsHelper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.basicAuth
import io.ktor.client.request.delete
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.http.isSuccess

class APIServiceImpl(
	private val client: HttpClient,
	private val sharedPrefsHelper: SharedPrefsHelper
) : APIService {

	override suspend fun getAuthToken(
		code: String,
		clientId: String,
		codeVerifier: String,
	): AccessTokenDto {
		return client.post("${Constants.OAUTH2_URL}token") {
			setBody(FormDataContent(Parameters.build {
				append("client_id", clientId)
				append("code", code)
				append("code_verifier", codeVerifier)
				append("grant_type", "authorization_code")
			}))
		}.body()
	}

	override suspend fun getRefreshToken(
		grantType: String,
		refreshToken: String
	): RefreshTokenDto {
		return client.post("${Constants.OAUTH2_URL}token") {
			basicAuth(Private.CLIENT_ID, "")
			header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())

			setBody(FormDataContent(Parameters.build {
				append("grant_type", "refresh_token")
				append("refresh_token", refreshToken)
			}))
		}.body()
	}

	override suspend fun getSeasonalAnime(
		year: Int,
		season: String,
		limit: Int
	): AnimeSeasonalDto {
		println(sharedPrefsHelper.getString("accessToken"))
		return client.get("${Constants.ANIME_URL}/season/$year/$season") {
			header(
				HttpHeaders.Authorization,
				"Bearer ${sharedPrefsHelper.getString("accessToken")}"
			)
			parameter(
				"fields",
				"id,title,main_picture,alternative_titles,start_date,end_date,synopsis,mean,rank,popularity,num_list_users,num_scoring_users,nsfw,created_at,updated_at,media_type,status,genres,my_list_status,num_episodes,start_season,broadcast,source,average_episode_duration,rating,pictures,background,related_anime,related_manga,recommendations,studios,statistics"
			)
			parameter("limit", limit)
		}.body()
	}

	override suspend fun getSeasonalAnime(
		url: String
	): AnimeSeasonalDto {
		return client.get(url) {
			header(
				HttpHeaders.Authorization,
				"Bearer ${sharedPrefsHelper.getString("accessToken")}"
			)
			parameter(
				"fields",
				"id,title,main_picture,alternative_titles,start_date,end_date,synopsis,mean,rank,popularity,num_list_users,num_scoring_users,nsfw,created_at,updated_at,media_type,status,genres,my_list_status,num_episodes,start_season,broadcast,source,average_episode_duration,rating,pictures,background,related_anime,related_manga,recommendations,studios,statistics"
			)
		}.body()
	}

	override suspend fun getSuggestedAnime(
		limit: Int,
		offset: Int
	): AnimeSuggestedDto {
		return client.get("${Constants.ANIME_URL}/suggestions") {
			header(
				HttpHeaders.Authorization,
				"Bearer ${sharedPrefsHelper.getString("accessToken")}"
			)
			parameter("limit", limit)
			parameter("offset", offset)
		}.body()
	}

	override suspend fun getSuggestedAnime(
		url: String
	): AnimeSuggestedDto {
		return client.get(url) {
			header(
				HttpHeaders.Authorization,
				"Bearer ${sharedPrefsHelper.getString("accessToken")}"
			)
		}.body()
	}

	override suspend fun getSearchedAnime(
		query: String,
		limit: Int,
		offset: Int,
	): AnimeSearchedDto {
		return client.get(Constants.ANIME_URL) {
			header(
				HttpHeaders.Authorization,
				"Bearer ${sharedPrefsHelper.getString("accessToken")}"
			)
			parameter("q", query)
			parameter("limit", limit)
			parameter("offset", offset)
			parameter("fields", "id,title,main_picture,mean,media_type,num_episodes,start_season")
		}.body()
	}

	override suspend fun getSearchedAnime(
		url: String
	): AnimeSearchedDto {
		return client.get(url) {
			header(
				HttpHeaders.Authorization,
				"Bearer ${sharedPrefsHelper.getString("accessToken")}"
			)
			parameter("fields", "id,title,main_picture,mean,media_type,num_episodes,start_season")
		}.body()
	}

	override suspend fun getUserAnimeList(
		status: String?,
		sort: String,
		limit: Int,
		offset: Int
	): AnimeUserListDto {
		return client.get(Constants.USER_ANIME_LIST_URL) {
			header(
				HttpHeaders.Authorization,
				"Bearer ${sharedPrefsHelper.getString("accessToken")}"
			)
			status?.let {
				parameter("status", status)
			}
			parameter("sort", sort)
			parameter("limit", limit)
			parameter("offset", offset)
			parameter(
				"fields", "list_status,num_episodes,start_season,main_picture,title,mean,media_type"
			)
		}.body()
	}

	override suspend fun getUserAnimeList(
		url: String
	): AnimeUserListDto {
		return client.get(url) {
			header(
				HttpHeaders.Authorization,
				"Bearer ${sharedPrefsHelper.getString("accessToken")}"
			)
			parameter(
				"fields", "list_status,num_episodes,start_season,main_picture,title,mean,media_type"
			)
		}.body()
	}

	override suspend fun getUserMangaList(
		status: String?,
		sort: String,
		limit: Int,
		offset: Int
	): MangaUserListDto {
		return client.get(Constants.USER_MANGA_LIST_URL) {
			header(
				HttpHeaders.Authorization,
				"Bearer ${sharedPrefsHelper.getString("accessToken")}"
			)
			status?.let {
				parameter("status", status)
			}
			parameter("sort", sort)
			parameter("limit", limit)
			parameter("offset", offset)
			parameter(
				"fields", "list_status,num_volumes,start_season,main_picture,title,mean,media_type"
			)
		}.body()
	}

	override suspend fun getUserMangaList(
		url: String
	): MangaUserListDto {
		return client.get(url) {
			header(
				HttpHeaders.Authorization,
				"Bearer ${sharedPrefsHelper.getString("accessToken")}"
			)
			parameter(
				"fields", "list_status,num_volumes,start_season,main_picture,title,mean,media_type"
			)
		}.body()
	}

	override suspend fun getUserProfile(): UserDto {
		return client.get("${Constants.BASE_URL}/users/@me") {
			header(
				HttpHeaders.Authorization,
				"Bearer ${sharedPrefsHelper.getString("accessToken")}"
			)
			parameter("fields", "anime_statistics")
		}.body()
	}

	override suspend fun getAnimeDetail(
		id: String
	) : AnimeDetailDto
	{
		val myListStatusFields = "start_date,finish_date,num_times_rewatched,is_rewatching,rewatch_value,priority,tags,comments"
		return client.get("${Constants.ANIME_URL}/${id}") {
			header(
				HttpHeaders.Authorization,
				"Bearer ${sharedPrefsHelper.getString("accessToken")}"
			)
			parameter("fields", "id,title,main_picture,alternative_titles,start_date,end_date,synopsis,mean,rank,popularity," +
					"num_list_users,num_scoring_users,media_type,status,genres,my_list_status{$myListStatusFields},num_episodes,start_season,broadcast," +
					"source,average_episode_duration,studios,opening_themes,ending_themes,related_anime{media_type},related_manga{media_type}," +
					"recommendations")
		}.body()
	}

	override suspend fun getMangaDetail(
		id: String
	): MangaDetailDto {
		return client.get("${Constants.MANGA_URL}/${id}") {
			header(
				HttpHeaders.Authorization,
				"Bearer ${sharedPrefsHelper.getString("accessToken")}"
			)
			parameter("fields", "id,title,main_picture,alternative_titles,start_date,end_date,synopsis,mean,rank,popularity," +
					"num_list_users,num_scoring_users,media_type,status,genres,my_list_status,num_chapters,num_volumes," +
					"source,authors{first_name,last_name},serialization,related_anime{media_type},related_manga{media_type}," +
					"recommendations")
		}.body()
	}

	override suspend fun updateMyAnimeListItem(
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
	): MyListStatusDto {
		return client.patch("${Constants.ANIME_URL}/${id}/my_list_status") {
			header(
				HttpHeaders.Authorization,
				"Bearer ${sharedPrefsHelper.getString("accessToken")}"
			)
			setBody(FormDataContent(Parameters.build {
				status?.let { append("status", it) }
				episodeCount?.let { append("num_watched_episodes", it.toString()) }
				score?.let { append("score", it.toString()) }
				startDate?.let { append("start_date", it) }
				finishDate?.let { append("finish_date", it) }
				tags?.let { append("tags", it) }
				priority?.let { append("priority", it.toString()) }
				isRewatching?.let { append("is_rewatching", it.toString()) }
				rewatchCount?.let { append("num_times_rewatched", it.toString()) }
				rewatchValue?.let { append("rewatch_value", it.toString()) }
				comments?.let { append("comments", it) }
			}))
		}.body()
	}

	override suspend fun updateMyMangaListItem(
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
	): MyListStatusDto {
		return client.patch("${Constants.MANGA_URL}/${id}/my_list_status") {
			header(
				HttpHeaders.Authorization,
				"Bearer ${sharedPrefsHelper.getString("accessToken")}"
			)
			setBody(FormDataContent(Parameters.build {
				status?.let { append("status", it) }
				chapterCount?.let { append("num_chapters_read", it.toString()) }
				volumeCount?.let { append("num_volumes_read", it.toString()) }
				score?.let { append("score", it.toString()) }
				startDate?.let { append("start_date", it) }
				finishDate?.let { append("finish_date", it) }
				tags?.let { append("tags", it) }
				priority?.let { append("priority", it.toString()) }
				isRereading?.let { append("is_rereading", it.toString()) }
				rereadCount?.let { append("num_times_reread", it.toString()) }
				rereadValue?.let { append("reread_value", it.toString()) }
				comments?.let { append("comments", it) }
			}))
		}.body()
	}

	override suspend fun deleteMyAnimeListItem(
		id: Int
	): Boolean {
		return client.delete("${Constants.ANIME_URL}/${id}/my_list_status") {
			header(
				HttpHeaders.Authorization,
				"Bearer ${sharedPrefsHelper.getString("accessToken")}"
			)
		}.status.isSuccess()
	}

	override suspend fun deleteMyMangaListItem(
		id: Int
	): Boolean {
		return client.delete("${Constants.MANGA_URL}/${id}/my_list_status") {
			header(
				HttpHeaders.Authorization,
				"Bearer ${sharedPrefsHelper.getString("accessToken")}"
			)
		}.status.isSuccess()
	}
}