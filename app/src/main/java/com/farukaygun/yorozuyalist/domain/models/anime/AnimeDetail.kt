package com.farukaygun.yorozuyalist.domain.models.anime

import com.farukaygun.yorozuyalist.domain.interfaces.MediaDetail
import com.farukaygun.yorozuyalist.domain.models.AlternativeTitles
import com.farukaygun.yorozuyalist.domain.models.Genre
import com.farukaygun.yorozuyalist.domain.models.MainPicture
import com.farukaygun.yorozuyalist.domain.models.MyListStatus
import com.farukaygun.yorozuyalist.domain.models.Recommendation
import com.farukaygun.yorozuyalist.domain.models.Related
import com.farukaygun.yorozuyalist.domain.models.enums.AnimeSource
import com.farukaygun.yorozuyalist.domain.models.enums.MediaStatus

data class AnimeDetail(
	override val id: Int,
	override val title: String,
	override val mainPicture: MainPicture?,
	override val alternativeTitles: AlternativeTitles,
	override val startDate: String?,
	override val endDate: String?,
	override val synopsis: String,
	override val mean: Double,
	override val rank: Int,
	override val popularity: Int,
	override val numListUsers: Int,
	override val numScoringUsers: Int,
	override val nsfw: String?,
	override val createdAt: String?,
	override val updatedAt: String?,
	override val mediaType: String,
	override val status: MediaStatus?,
	override val genres: List<Genre>,
	val numEpisodes: Int,
	override val myListStatus: MyListStatus?,
	val startSeason: StartSeason?,
	val broadcast: Broadcast?,
	val source: AnimeSource,
	val averageEpisodeDuration: Int,
	val rating: String?,
	override val pictures: List<MainPicture?>?,
	override val background: String?,
	override val relatedAnime: List<Related?>?,
	override val relatedManga: List<Related?>?,
	override val recommendations: List<Recommendation>?,
	val studios: List<Studio>,
	val statistics: Statistics?
) : MediaDetail()