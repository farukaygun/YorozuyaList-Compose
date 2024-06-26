package com.farukaygun.yorozuyalist.domain.model

import com.google.gson.annotations.SerializedName

data class ListStatus(
	@SerializedName("status")
	val status: String,

	@SerializedName("score")
	val score: String,

	@SerializedName(value = "num_episodes_watched", alternate = ["num_chapters_read"])
	val numEpisodesWatched: Int,

	@SerializedName("is_rewatching")
	val isRewatching: Boolean,

	@SerializedName("updated_at")
	val updatedAt: String,
)