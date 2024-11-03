package com.farukaygun.yorozuyalist.util

import com.farukaygun.yorozuyalist.domain.interfaces.Formattable

object Constants {
	const val OAUTH2_URL = "https://myanimelist.net/v1/oauth2/"
	const val BASE_URL = "https://api.myanimelist.net/v2"
	const val MANGA_URL = "${BASE_URL}/manga"
	const val ANIME_URL = "${BASE_URL}/anime"
	const val USER_ANIME_LIST_URL = "${BASE_URL}/users/@me/animelist"
	const val USER_MANGA_LIST_URL = "${BASE_URL}/users/@me/mangalist"
	const val YOROZUYA_PAGE_LINK = "app://com.farukaygun.yorozuyalist"
}

enum class Sort(val value: String) : Formattable {
	SCORE("list_score"),
	UPDATED_AT("list_updated_at"),
	ANIME_TITLE("anime_title"),
	MANGA_TITLE("manga_title"),
	ANIME_START_DATE("anime_start_date"),
	MANGA_START_DATE("manga_start_date"),
	ANIME_ID("anime_id"),
	MANGA_ID("manga_id");

	override fun format() = value
}

enum class ScreenType {
	ANIME,
	MANGA;
}

enum class GridListType {
	SEASONAL_ANIME_LIST,
	SUGGESTED_ANIME_LIST
}