package com.farukaygun.yorozuyalist.domain.models.anime

import com.farukaygun.yorozuyalist.domain.interfaces.MediaList
import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.Paging

data class AnimeSeasonal(
	override var data: List<Data>,
	override val paging: Paging,
	val season: Season
) : MediaList
