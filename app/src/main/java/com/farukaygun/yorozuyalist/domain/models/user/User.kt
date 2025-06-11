package com.farukaygun.yorozuyalist.domain.models.user

data class User(
	val userAnimeStatistics: UserAnimeStatistics,
	val birthday: String,
	val id: Int,
	val joinedAt: String,
	val location: String,
	val name: String,
	val picture: String
)