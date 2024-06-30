package com.farukaygun.yorozuyalist.util

object Constants {
	const val OAUTH2_URL = "https://myanimelist.net/v1/oauth2/"
	const val BASE_URL = "https://api.myanimelist.net/v2"
	const val MANGA_URL = "${BASE_URL}/manga"
	const val ANIME_URL = "${BASE_URL}/anime"
	const val USER_ANIME_LIST_URL = "${BASE_URL}/users/@me/animelist"
	const val USER_MANGA_LIST_URL = "${BASE_URL}/users/@me/mangalist"
	const val YOROZUYA_PAGELINK = "app://com.farukaygun.yorozuyalist"

	const val CURRENTLY_AIRING = "currently_airing"
	const val NOT_YET_AIRED = "not_yet_aired"
	const val FINISHED_AIRING = "finished_airing"
}

enum class MediaType {
	ANIME,
	MANGA
}

enum class RankingType(val value: String) {
	ALL("all"),
	AIRING("airing"),
	UPCOMING("upcoming"),
	TV("tv"),
	OVA("ova"),
	MOVIE("movie"),
	SPECIAL("special"),
	POPULARITY("bypopularity"),
	FAVORITE("favorite")
}

enum class Status(val value: String) {
	WATCHING("watching"),
	READING("reading"),
	COMPLETED("completed"),
	ON_HOLD("on_hold"),
	DROPPED("dropped"),
	PLAN_TO_WATCH("plan_to_watch"),
	PLAN_TO_READ("plan_to_read")
}

enum class Sort(val value: String) {
	SCORE("list_score"),
	UPDATED_AT("list_updated_at"),
	ANIME_TITLE("anime_title"),
	MANGA_TITLE("manga_title"),
	ANIME_START_DATE("anime_start_date"),
	MANGA_START_DATE("manga_start_date"),
	ANIME_ID("anime_id"),
	MANGA_ID("manga_id"),
}