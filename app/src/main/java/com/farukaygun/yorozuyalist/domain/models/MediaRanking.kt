package com.farukaygun.yorozuyalist.domain.models

import com.farukaygun.yorozuyalist.domain.interfaces.MediaList
import com.google.gson.annotations.SerializedName

data class MediaRanking(
	@SerializedName("data")
	override val data: List<Data>,
	@SerializedName("paging")
	override val paging: Paging
) : MediaList