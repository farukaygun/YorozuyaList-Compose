package com.farukaygun.yorozuyalist.domain.models.user

data class UserAnimeStatistics(
	val meanScore: Double,
	val numDays: Double,
	val numDaysCompleted: Double,
	val numDaysDropped: Double,
	val numDaysOnHold: Int,
	val numDaysWatched: Double,
	val numDaysWatching: Double,
	val numEpisodes: Int,
	val numTimesRewatched: Int,
	val numItems: Float,
	val numItemsCompleted: Int,
	val numItemsDropped: Int,
	val numItemsOnHold: Int,
	val numItemsPlanToWatch: Int,
	val numItemsWatching: Int
)
