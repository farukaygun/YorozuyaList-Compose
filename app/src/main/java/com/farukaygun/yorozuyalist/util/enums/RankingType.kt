package com.farukaygun.yorozuyalist.util.enums

import com.google.gson.annotations.SerializedName

enum class RankingType(val value: String) {
	@SerializedName("all")
	ALL("all"),

	@SerializedName("airing")
	AIRING("airing"),

	@SerializedName("upcoming")
	UPCOMING("upcoming"),

	@SerializedName("tv")
	TV("tv"),

	@SerializedName("ova")
	OVA("ova"),

	@SerializedName("movie")
	MOVIE("movie"),

	@SerializedName("special")
	SPECIAL("special"),

	@SerializedName("bypopularity")
	BY_POPULARITY("bypopularity"),

	@SerializedName("favorite")
	FAVORITE("favorite"),
}