package com.farukaygun.yorozuyalist.domain.model.anime

import com.farukaygun.yorozuyalist.domain.model.Data
import com.farukaygun.yorozuyalist.domain.model.Paging
import com.google.gson.annotations.SerializedName

data class AnimeUserList(
	@SerializedName("data")
	val data: List<Data>,
	@SerializedName("paging")
	val paging: Paging
)