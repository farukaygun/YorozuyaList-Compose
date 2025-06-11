package com.farukaygun.yorozuyalist.data.remote.dto

import com.farukaygun.yorozuyalist.data.remote.dto.anime.BroadcastDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.StartSeasonDto
import com.farukaygun.yorozuyalist.data.remote.dto.anime.toBroadcast
import com.farukaygun.yorozuyalist.data.remote.dto.anime.toStartSeason
import com.farukaygun.yorozuyalist.domain.models.Node
import com.farukaygun.yorozuyalist.domain.models.enums.MediaType
import com.google.gson.annotations.SerializedName

data class NodeDto(
	@SerializedName("id")
	val id: Int,
	@SerializedName("title")
	val title: String,
	@SerializedName("broadcast")
	val broadcast: BroadcastDto?,
	@SerializedName("main_picture")
	val mainPicture: MainPictureDto,
	@SerializedName("mean")
	val mean: String?,
	@SerializedName("rank")
	val rank: Int?,
	@SerializedName("media_type")
	val mediaType: String?,
	@SerializedName(value = "num_episodes", alternate = ["num_chapters"])
	val numEpisodes: Int,
	@SerializedName("start_season")
	val startSeason: StartSeasonDto?,
	@SerializedName("num_list_users")
	val numListUsers: Int,
	@SerializedName("status")
	val status: String?
)

fun NodeDto.toNode(): Node {
	return Node(
		id = id,
		title = title,
		broadcast = broadcast?.toBroadcast(),
		mainPicture = mainPicture.toMainPicture(),
		mean = mean,
		rank = rank,
		mediaType = MediaType.TV,
		numEpisodes = numEpisodes,
		startSeason = startSeason?.toStartSeason(),
		numListUsers = numListUsers,
		status = status
	)
}