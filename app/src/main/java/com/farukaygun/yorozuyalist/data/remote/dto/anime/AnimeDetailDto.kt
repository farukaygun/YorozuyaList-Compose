package com.farukaygun.yorozuyalist.data.remote.dto.anime

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
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeDetail
import com.farukaygun.yorozuyalist.domain.models.enums.AnimeSource
import com.farukaygun.yorozuyalist.domain.models.enums.MediaStatus
import com.google.gson.annotations.SerializedName

data class AnimeDetailDto(
	@SerializedName("id")
	val id: Int,
	@SerializedName("title")
	val title: String,
	@SerializedName("main_picture")
	val mainPicture: MainPictureDto?,
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
	@SerializedName("num_episodes")
	val numEpisodes: Int,
	@SerializedName("my_list_status")
	val myListStatus: MyListStatusDto?,
	@SerializedName("start_season")
	val startSeason: StartSeasonDto?,
	@SerializedName("broadcast")
	val broadcast: BroadcastDto?,
	@SerializedName("source")
	val source: AnimeSource,
	@SerializedName("average_episode_duration")
	val averageEpisodeDuration: Int,
	@SerializedName("rating")
	val rating: String?,
	@SerializedName("pictures")
	val pictures: List<MainPictureDto?>?,
	@SerializedName("background")
	val background: String?,
	@SerializedName("related_anime")
	val relatedAnimeDto: List<RelatedDto?>?,
	@SerializedName("related_manga")
	val relatedMangaDto: List<RelatedDto?>?,
	@SerializedName("recommendations")
	val recommendations: List<RecommendationDto>?,
	@SerializedName("studios")
	val studios: List<StudioDto>,
	@SerializedName("statistics")
	val statistics: StatisticsDto?,
)

fun AnimeDetailDto.toAnimeDetail(): AnimeDetail {
	return AnimeDetail(
		id = id,
		title = title,
		mainPicture = mainPicture?.toMainPicture(),
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
		numEpisodes = numEpisodes,
		myListStatus = myListStatus?.toMyListStatus(),
		startSeason = startSeason?.toStartSeason(),
		broadcast = broadcast?.toBroadcast(),
		source = source,
		averageEpisodeDuration = averageEpisodeDuration,
		rating = rating,
		pictures = pictures?.map { it?.toMainPicture() },
		background = background,
		relatedAnime = relatedAnimeDto?.map { it?.toRelated() },
		relatedManga = relatedMangaDto?.map { it?.toRelated() },
		recommendations = recommendations?.map { it.toRecommendation() },
		studios = studios.map { it.toStudio() },
		statistics = statistics?.toStatistics()
	)
}