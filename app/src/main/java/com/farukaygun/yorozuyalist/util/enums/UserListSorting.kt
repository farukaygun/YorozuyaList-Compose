package com.farukaygun.yorozuyalist.util.enums

import com.google.gson.annotations.SerializedName

enum class UserListSorting(val value: String) {
	@SerializedName("list_score")
	SCORE("list_score"),

	@SerializedName("list_updated_at")
	UPDATED_AT("list_updated_at"),

	@SerializedName("anime_title")
	ANIME_TITLE("anime_title"),

	@SerializedName("manga_title")
	MANGA_TITLE("manga_title"),

	@SerializedName("anime_start_date")
	ANIME_START_DATE("anime_start_date"),

	@SerializedName("manga_start_date")
	MANGA_START_DATE("manga_start_date"),

	@SerializedName("anime_id")
	ANIME_ID("anime_id"),

	@SerializedName("manga_id")
	MANGA_ID("manga_id");
}