package com.farukaygun.yorozuyalist.util

object Constants {
	const val OAUTH2_URL = "https://myanimelist.net/v1/oauth2/"
	private const val BASE_URL = "https://api.myanimelist.net/v2"
	const val MANGA_URL = "${BASE_URL}/manga"
	const val ANIME_URL = "${BASE_URL}/anime"
	const val YOROZUYA_PAGELINK = "app://com.farukaygun.yorozuyalist"

	const val STATUS_ALL = "all"
	const val WATCHING = "watching"
	const val COMPLETED = "completed"
	const val ON_HOLD = "on_hold"
	const val DROPPED = "dropped"
	const val PTW = "plan_to_watch"
	const val PTR = "plan_to_read"
	const val READING = "reading"

	const val CURRENTLY_AIRING = "currently_airing"
	const val NOT_YET_AIRED = "not_yet_aired"
	const val FINISHED_AIRING = "finished_airing"
}