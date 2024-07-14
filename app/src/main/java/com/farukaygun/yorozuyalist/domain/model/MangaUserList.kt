package com.farukaygun.yorozuyalist.domain.model

import com.farukaygun.yorozuyalist.domain.interfaces.MediaList
import com.google.gson.annotations.SerializedName

data class MangaUserList(
	@SerializedName("data")
	override val data: List<Data>,
	@SerializedName("paging")
	override val paging: Paging
) : MediaList