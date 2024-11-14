package com.farukaygun.yorozuyalist.domain.models

import com.farukaygun.yorozuyalist.domain.models.enums.MyListMediaStatus
import com.google.gson.annotations.SerializedName

data class MyListStatus(
	@SerializedName("status")
	var status: MyListMediaStatus?,
	@SerializedName("score")
	var score: Int,
	@SerializedName("updated_at")
	val updatedAt: String,
	@SerializedName("start_date")
	val startDate: String?,
	@SerializedName("finish_date")
	val finishDate: String?,
	@SerializedName("num_episodes_watched")
	var numEpisodesWatched: Int? = null,
	@SerializedName("num_chapters_read")
	var numChaptersRead: Int? = null,
	@SerializedName("num_volumes_read")
	var numVolumesRead: Int? = null,
	@SerializedName("is_rewatching")
	val isRewatching: Boolean,
	@SerializedName("num_times_rewatched")
	val numTimesRewatched: Int?,
	@SerializedName("rewatch_value")
	val rewatchValue: Int?,
	@SerializedName("tags")
	val tags: List<String>?,
	@SerializedName("priority")
	val priority: Int?,
	@SerializedName("comments")
	val comments: String?,
)