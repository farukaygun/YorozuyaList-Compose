package com.farukaygun.yorozuyalist.domain.models

import com.farukaygun.yorozuyalist.domain.interfaces.MediaList

data class MediaRanking(
	override val data: List<Data>,
	override val paging: Paging
) : MediaList