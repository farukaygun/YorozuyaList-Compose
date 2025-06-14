package com.farukaygun.yorozuyalist.util

import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.MainPicture
import com.farukaygun.yorozuyalist.domain.models.MyListStatus
import com.farukaygun.yorozuyalist.domain.models.Node
import com.farukaygun.yorozuyalist.domain.models.Ranking
import com.farukaygun.yorozuyalist.domain.models.anime.Broadcast
import com.farukaygun.yorozuyalist.domain.models.anime.StartSeason
import com.farukaygun.yorozuyalist.domain.models.enums.MediaType
import com.farukaygun.yorozuyalist.domain.models.enums.MyListMediaStatus
import com.farukaygun.yorozuyalist.domain.models.enums.Season

object Constants {
	const val HOME_URL = "https://myanimelist.net"
	const val OAUTH2_URL = "https://myanimelist.net/v1/oauth2/"
	const val BASE_URL = "https://api.myanimelist.net/v2"
	const val MANGA_URL = "${BASE_URL}/manga"
	const val ANIME_URL = "${BASE_URL}/anime"
	const val USER_ANIME_LIST_URL = "${BASE_URL}/users/@me/animelist"
	const val USER_MANGA_LIST_URL = "${BASE_URL}/users/@me/mangalist"
	const val YOROZUYA_PAGE_LINK = "app://com.farukaygun.yorozuyalist"
	val PREVIEW_SAMPLE_DATA = Data(
		node = Node(
			id = 52991,
			title = "Sousou no Frieren",
			mainPicture = MainPicture(
				medium = "https:\\/\\/cdn.myanimelist.net\\/images\\/anime\\/1015\\/138006.jpg",
				large = "https:\\/\\/cdn.myanimelist.net\\/images\\/anime\\/1015\\/138006l.jpg"
			),
			status = "finished_airing",
			mean = "9.38",
			mediaType = MediaType.TV,
			startSeason = StartSeason(
				year = 2023,
				season = Season.FALL
			),
			numListUsers = 701406,
			numEpisodes = 12,
			broadcast = Broadcast(
				dayOfTheWeek = "Saturday",
				startTime = "00:00"
			),
			rank = 1
		),
		myListStatus = MyListStatus(
			status = MyListMediaStatus.WATCHING,
			score = 10,
			numEpisodesWatched = 12,
			isRewatching = false,
			updatedAt = "",
			startDate = "",
			finishDate = "",
			numTimesRewatched = 0,
			rewatchValue = 0,
			tags = emptyList(),
			priority = 0,
			comments = "",
			numChaptersRead = 0,
			numVolumesRead = 0
		),
		ranking = Ranking(
			rank = 1,
		),
		rankingType = "Score"
	)
}