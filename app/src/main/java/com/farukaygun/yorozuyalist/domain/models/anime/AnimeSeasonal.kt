package com.farukaygun.yorozuyalist.domain.models.anime

import com.farukaygun.yorozuyalist.domain.interfaces.MediaList
import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.Paging
import com.google.gson.annotations.SerializedName

data class AnimeSeasonal(
	@SerializedName("data")
	override var data: List<Data>,
	@SerializedName("paging")
	override val paging: Paging,
	@SerializedName("season")
	val season: Season,
) : MediaList
