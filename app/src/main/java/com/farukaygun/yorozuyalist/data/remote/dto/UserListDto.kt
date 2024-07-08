package com.farukaygun.yorozuyalist.data.remote.dto

import com.farukaygun.yorozuyalist.domain.model.Data
import com.farukaygun.yorozuyalist.domain.model.Paging
import com.farukaygun.yorozuyalist.domain.model.UserList
import com.google.gson.annotations.SerializedName

class UserListDto(
	@SerializedName("data")
	val data: List<Data>,
	@SerializedName("paging")
	val paging: Paging
)

fun UserListDto.toUserList() : UserList {
	return UserList(
		data = data,
		paging = paging
	)
}