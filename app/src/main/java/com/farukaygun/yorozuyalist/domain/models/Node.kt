package com.farukaygun.yorozuyalist.domain.models

import com.farukaygun.yorozuyalist.domain.models.anime.Broadcast
import com.farukaygun.yorozuyalist.domain.models.anime.StartSeason
import com.farukaygun.yorozuyalist.domain.models.enums.MediaType

data class Node(
	val id: Int,
	val title: String,
	val broadcast: Broadcast?,
	val mainPicture: MainPicture?,
	val mean: String?,
	val rank: Int?,
	val mediaType: MediaType?,
	val numEpisodes: Int,
	val startSeason: StartSeason?,
	val numListUsers: Int,
	val status: String?
)
