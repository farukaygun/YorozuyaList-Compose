package com.farukaygun.yorozuyalist.domain.interfaces

import com.farukaygun.yorozuyalist.domain.models.AlternativeTitles
import com.farukaygun.yorozuyalist.domain.models.Genre
import com.farukaygun.yorozuyalist.domain.models.MainPicture
import com.farukaygun.yorozuyalist.domain.models.MyListStatus
import com.farukaygun.yorozuyalist.domain.models.Recommendation
import com.farukaygun.yorozuyalist.domain.models.Related
import com.farukaygun.yorozuyalist.domain.models.enums.MediaStatus

abstract class MediaDetail {
	abstract val id: Int
	abstract val title: String
	abstract val mainPicture: MainPicture?
	abstract val alternativeTitles: AlternativeTitles
	abstract val startDate: String?
	abstract val endDate: String?
	abstract val synopsis: String
	abstract val mean: Double
	abstract val rank: Int
	abstract val popularity: Int
	abstract val numListUsers: Int
	abstract val numScoringUsers: Int
	abstract val nsfw: String?
	abstract val createdAt: String?
	abstract val updatedAt: String?
	abstract val mediaType: String
	abstract val status: MediaStatus?
	abstract val genres: List<Genre>
	abstract val myListStatus: MyListStatus?
	abstract val pictures: List<MainPicture?>?
	abstract val background: String?
	abstract val relatedAnime: List<Related?>?
	abstract val relatedManga: List<Related?>?
	abstract val recommendations: List<Recommendation>?
}