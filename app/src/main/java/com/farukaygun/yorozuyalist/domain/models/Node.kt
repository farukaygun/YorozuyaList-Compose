package com.farukaygun.yorozuyalist.domain.models

import com.farukaygun.yorozuyalist.domain.models.anime.Broadcast
import com.farukaygun.yorozuyalist.domain.models.anime.StartSeason
import com.farukaygun.yorozuyalist.domain.models.enums.MediaType
import com.google.gson.annotations.SerializedName

data class Node(
	@SerializedName("id")
	val id: Int,
	@SerializedName("title")
	val title: String,
	@SerializedName("broadcast")
	val broadcast: Broadcast?,
	@SerializedName("main_picture")
	val mainPicture: MainPicture,
	@SerializedName("mean")
	val mean: String?,
	@SerializedName("media_type")
	val mediaType: MediaType?,
	@SerializedName(value = "num_episodes", alternate = ["num_chapters"])
	val numEpisodes: Int,
	@SerializedName("start_season")
	val startSeason: StartSeason?,
	@SerializedName("num_list_users")
	val numListUsers: Int,
	@SerializedName("status")
	val status: String?,
)
