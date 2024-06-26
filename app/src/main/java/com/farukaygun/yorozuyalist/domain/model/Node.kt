package com.farukaygun.yorozuyalist.domain.model

import com.farukaygun.yorozuyalist.domain.model.anime.Broadcast
import com.farukaygun.yorozuyalist.domain.model.anime.MainPicture
import com.farukaygun.yorozuyalist.domain.model.anime.StartSeason
import com.google.gson.annotations.SerializedName

data class Node(
	@SerializedName("id")
	val id: Int,

	@SerializedName("title")
	val title: String,

	@SerializedName("broadcast")
	val broadcast: Broadcast? = null,

	@SerializedName("main_picture")
	val mainPicture: MainPicture,

	@SerializedName("mean")
	val mean: String,

	@SerializedName("media_type")
	val mediaType: String,

	@SerializedName(value = "num_episodes", alternate = ["num_chapters"])
	val numEpisodes: Int? = 0,

	@SerializedName("start_season")
	val startSeason: StartSeason,

	@SerializedName("num_list_users")
	val numListUsers: Int,

	@SerializedName("status")
	val status: String,
)
