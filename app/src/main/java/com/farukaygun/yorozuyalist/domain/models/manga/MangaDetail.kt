package com.farukaygun.yorozuyalist.domain.models.manga

import com.farukaygun.yorozuyalist.domain.interfaces.MediaDetail
import com.farukaygun.yorozuyalist.domain.models.AlternativeTitles
import com.farukaygun.yorozuyalist.domain.models.Genre
import com.farukaygun.yorozuyalist.domain.models.MainPicture
import com.farukaygun.yorozuyalist.domain.models.MyListStatus
import com.farukaygun.yorozuyalist.domain.models.Recommendation
import com.farukaygun.yorozuyalist.domain.models.Related
import com.farukaygun.yorozuyalist.domain.models.enums.MediaStatus

data class MangaDetail(
	override val id: Int,
	override val title: String,
	override val mainPicture: MainPicture,
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
	val numVolumes: Int,
	val numChapters: Int,
	override val myListStatus: MyListStatus?,
	val authors: List<Author>,
	override val pictures: List<MainPicture?>?,
	override val background: String?,
	override val relatedAnime: List<Related>,
	override val relatedManga: List<Related>,
	override val recommendations: List<Recommendation>,
	val serialization: List<Serialization>
) : MediaDetail()
