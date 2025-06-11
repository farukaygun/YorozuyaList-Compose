package com.farukaygun.yorozuyalist.domain.models.anime

import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.Paging

data class AnimeSearched(
	val data: List<Data>,
	val paging: Paging
)
