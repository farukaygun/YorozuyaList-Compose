package com.farukaygun.yorozuyalist.data.remote.dto.manga

import com.farukaygun.yorozuyalist.data.remote.dto.AlternativeTitlesDto
import com.farukaygun.yorozuyalist.data.remote.dto.GenreDto
import com.farukaygun.yorozuyalist.data.remote.dto.MainPictureDto
import com.farukaygun.yorozuyalist.data.remote.dto.MyListStatusDto
import com.farukaygun.yorozuyalist.data.remote.dto.RecommendationDto
import com.farukaygun.yorozuyalist.data.remote.dto.RelatedDto
import com.farukaygun.yorozuyalist.data.remote.dto.toAlternativeTitles
import com.farukaygun.yorozuyalist.data.remote.dto.toGenre
import com.farukaygun.yorozuyalist.data.remote.dto.toMainPicture
import com.farukaygun.yorozuyalist.data.remote.dto.toMyListStatus
import com.farukaygun.yorozuyalist.data.remote.dto.toRecommendation
import com.farukaygun.yorozuyalist.data.remote.dto.toRelated
import com.farukaygun.yorozuyalist.domain.models.enums.MediaStatus
import com.farukaygun.yorozuyalist.domain.models.manga.MangaDetail
import com.google.gson.annotations.SerializedName

data class MangaDetailDto(
	@SerializedName("id")
	val id: Int,
	@SerializedName("title")
	val title: String,
	@SerializedName("main_picture")
	val mainPicture: MainPictureDto,
	@SerializedName("alternative_titles")
	val alternativeTitles: AlternativeTitlesDto,
	@SerializedName("start_date")
	val startDate: String?,
	@SerializedName("end_date")
	val endDate: String?,
	@SerializedName("synopsis")
	val synopsis: String,
	@SerializedName("mean")
	val mean: Double,
	@SerializedName("rank")
	val rank: Int,
	@SerializedName("popularity")
	val popularity: Int,
	@SerializedName("num_list_users")
	val numListUsers: Int,
	@SerializedName("num_scoring_users")
	val numScoringUsers: Int,
	@SerializedName("nsfw")
	val nsfw: String?,
	@SerializedName("created_at")
	val createdAt: String?,
	@SerializedName("updated_at")
	val updatedAt: String?,
	@SerializedName("media_type")
	val mediaType: String,
	@SerializedName("status")
	val status: MediaStatus?,
	@SerializedName("genres")
	val genres: List<GenreDto>,
	@SerializedName("num_volumes")
	val numVolumes: Int,
	@SerializedName("num_chapters")
	val numChapters: Int,
	@SerializedName("my_list_status")
	val myListStatus: MyListStatusDto?,
	@SerializedName("authors")
	val authors: List<AuthorDto>,
	@SerializedName("pictures")
	val pictures: List<MainPictureDto?>?,
	@SerializedName("background")
	val background: String?,
	@SerializedName("related_anime")
	val relatedAnime: List<RelatedDto>,
	@SerializedName("related_manga")
	val relatedManga: List<RelatedDto>,
	@SerializedName("recommendations")
	val recommendations: List<RecommendationDto>,
	@SerializedName("serialization")
	val serialization: List<SerializationDto>,
)

// to model
fun MangaDetailDto.toMangaDetail(): MangaDetail {
	return MangaDetail(
		id = id,
		title = title,
		mainPicture = mainPicture.toMainPicture(),
		alternativeTitles = alternativeTitles.toAlternativeTitles(),
		startDate = startDate,
		endDate = endDate,
		synopsis = synopsis,
		mean = mean,
		rank = rank,
		popularity = popularity,
		numListUsers = numListUsers,
		numScoringUsers = numScoringUsers,
		nsfw = nsfw,
		createdAt = createdAt,
		updatedAt = updatedAt,
		mediaType = mediaType,
		status = status,
		genres = genres.map { it.toGenre() },
		numVolumes = numVolumes,
		numChapters = numChapters,
		myListStatus = myListStatus?.toMyListStatus(),
		authors = authors.map { it.toAuthor() },
		pictures = pictures?.map { it?.toMainPicture() },
		background = background,
		relatedAnime = relatedAnime.map { it.toRelated() },
		relatedManga = relatedManga.map { it.toRelated() },
		recommendations = recommendations.map { it.toRecommendation() },
		serialization = serialization.map { it.toSerialization() },
	)
}