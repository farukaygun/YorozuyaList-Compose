package com.farukaygun.yorozuyalist.domain.models

import com.farukaygun.yorozuyalist.domain.models.enums.MyListMediaStatus

data class MyListStatus(
	var status: MyListMediaStatus?,
	var score: Int,
	val updatedAt: String,
	val startDate: String?,
	val finishDate: String?,
	var numEpisodesWatched: Int?,
	var numChaptersRead: Int?,
	var numVolumesRead: Int?,
	val isRewatching: Boolean,
	val numTimesRewatched: Int?,
	val rewatchValue: Int?,
	val tags: List<String>?,
	val priority: Int?,
	val comments: String?
)