package com.farukaygun.yorozuyalist.domain.models.manga

import com.farukaygun.yorozuyalist.domain.interfaces.MediaList
import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.Paging

data class MangaUserList(
	override val data: List<Data>,
	override val paging: Paging
) : MediaList