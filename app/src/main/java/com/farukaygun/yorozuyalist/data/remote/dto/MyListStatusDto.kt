package com.farukaygun.yorozuyalist.data.remote.dto

import com.farukaygun.yorozuyalist.domain.models.MyListStatus
import com.farukaygun.yorozuyalist.domain.models.enums.MyListMediaStatus
import com.google.gson.annotations.SerializedName

data class MyListStatusDto(
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
	var numEpisodesWatched: Int?,
	@SerializedName("num_chapters_read")
	var numChaptersRead: Int?,
	@SerializedName("num_volumes_read")
	var numVolumesRead: Int?,
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

fun MyListStatusDto.toMyListStatus(): MyListStatus {
	return MyListStatus(
		status = status,
		score = score,
		updatedAt = updatedAt,
		startDate = startDate,
		finishDate = finishDate,
		numEpisodesWatched = numEpisodesWatched,
		numChaptersRead = numChaptersRead,
		numVolumesRead = numVolumesRead,
		isRewatching = isRewatching,
		numTimesRewatched = numTimesRewatched,
		rewatchValue = rewatchValue,
		tags = tags,
		priority = priority,
		comments = comments
	)
}