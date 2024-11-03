package com.farukaygun.yorozuyalist.domain.models.manga

import com.farukaygun.yorozuyalist.domain.interfaces.MediaDetail
import com.farukaygun.yorozuyalist.domain.models.AlternativeTitles
import com.farukaygun.yorozuyalist.domain.models.Genre
import com.farukaygun.yorozuyalist.domain.models.MainPicture
import com.farukaygun.yorozuyalist.domain.models.MyListStatus
import com.farukaygun.yorozuyalist.domain.models.Recommendation
import com.farukaygun.yorozuyalist.domain.models.Related
import com.farukaygun.yorozuyalist.domain.models.enums.MediaStatus
import com.google.gson.annotations.SerializedName

data class MangaDetail(
	@SerializedName("id")
	override val id: Int,
	@SerializedName("title")
	override val title: String,
	@SerializedName("main_picture")
	override val mainPicture: MainPicture,
	@SerializedName("alternative_titles")
	override val alternativeTitles: AlternativeTitles,
	@SerializedName("start_date")
	override val startDate: String?,
	@SerializedName("end_date")
	override val endDate: String?,
	@SerializedName("synopsis")
	override val synopsis: String,
	@SerializedName("mean")
	override val mean: Double,
	@SerializedName("rank")
	override val rank: Int,
	@SerializedName("popularity")
	override val popularity: Int,
	@SerializedName("num_list_users")
	override val numListUsers: Int,
	@SerializedName("num_scoring_users")
	override val numScoringUsers: Int,
	@SerializedName("nsfw")
	override val nsfw: String?,
	@SerializedName("created_at")
	override val createdAt: String?,
	@SerializedName("updated_at")
	override val updatedAt: String?,
	@SerializedName("media_type")
	override val mediaType: String,
	@SerializedName("status")
	override val status: MediaStatus?,
	@SerializedName("genres")
	override val genres: List<Genre>,
	@SerializedName("num_volumes")
	val numVolumes: Int,
	@SerializedName("num_chapters")
	val numChapters: Int,
	@SerializedName("my_list_status")
	override val myListStatus: MyListStatus?,
	@SerializedName("authors")
	val authors: List<Author>,
	@SerializedName("pictures")
	override val pictures: List<MainPicture?>?,
	@SerializedName("background")
	override val background: String?,
	@SerializedName("related_anime")
	override val relatedAnime: List<Related>,
	@SerializedName("related_manga")
	override val relatedManga: List<Related>,
	@SerializedName("recommendations")
	override val recommendations: List<Recommendation>,
	@SerializedName("serialization")
	val serialization: List<Serialization>,
) : MediaDetail()
