package com.farukaygun.yorozuyalist.domain.models.anime

import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.Paging
import com.google.gson.annotations.SerializedName

data class AnimeSearched(
	@SerializedName("data")
	val data: List<Data>,
	@SerializedName("paging")
	val paging: Paging
)
