package com.farukaygun.yorozuyalist.data.remote.dto.user

import com.farukaygun.yorozuyalist.domain.model.user.User
import com.farukaygun.yorozuyalist.domain.model.user.UserAnimeStatistics
import com.google.gson.annotations.SerializedName

data class UserDto(
	@SerializedName("anime_statistics")
	val userAnimeStatistics: UserAnimeStatistics,
	@SerializedName("birthday")
	val birthday: String,
	@SerializedName("id")
	val id: Int,
	@SerializedName("joined_at")
	val joinedAt: String,
	@SerializedName("location")
	val location: String,
	@SerializedName("name")
	val name: String,
	@SerializedName("picture")
	val picture: String,
)

fun UserDto.toUser() : User {
	return User(
		userAnimeStatistics = userAnimeStatistics,
		birthday = birthday,
		id = id,
		joinedAt = joinedAt,
		location = location,
		name = name,
		picture = picture
	)
}
