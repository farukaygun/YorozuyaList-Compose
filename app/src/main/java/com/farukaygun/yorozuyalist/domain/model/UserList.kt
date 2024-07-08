package com.farukaygun.yorozuyalist.domain.model

import com.google.gson.annotations.SerializedName

data class UserList(
	@SerializedName("data")
	val data: List<Data>,
	@SerializedName("paging")
	val paging: Paging
)