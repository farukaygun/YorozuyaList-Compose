package com.farukaygun.yorozuyalist.domain.models.enums

import com.google.gson.annotations.SerializedName

enum class MediaStatus(val displayName: String, val apiName: String) {
	@SerializedName("finished_airing")
	FINISHED_AIRING("Finished Airing", "finished_airing"),
	@SerializedName("currently_airing")
	CURRENTLY_AIRING("Currently Airing", "currently_airing"),
	@SerializedName("not_yet_aired")
	NOT_YET_AIRED("Not Yet Aired", "not_yet_aired"),
	@SerializedName("not_yet_published")
	NOT_YET_PUBLISHED("Not Yet Published", "not_yet_published")
}