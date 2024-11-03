package com.farukaygun.yorozuyalist.domain.models.anime

import com.farukaygun.yorozuyalist.domain.interfaces.MediaList
import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.Paging
import com.google.gson.annotations.SerializedName

data class AnimeSuggested(
	@SerializedName("data")
	override val data: List<Data>,
	@SerializedName("paging")
	override val paging: Paging,
) : MediaList