package com.farukaygun.yorozuyalist.data.remote.dto.anime

import com.farukaygun.yorozuyalist.data.remote.dto.DataDto
import com.farukaygun.yorozuyalist.data.remote.dto.PagingDto
import com.farukaygun.yorozuyalist.data.remote.dto.toData
import com.farukaygun.yorozuyalist.data.remote.dto.toPaging
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeSeasonal
import com.google.gson.annotations.SerializedName

data class AnimeSeasonalDto(
	@SerializedName("data")
	var data: List<DataDto>,
	@SerializedName("paging")
	val paging: PagingDto,
	@SerializedName("season")
	val season: SeasonDto
)

fun AnimeSeasonalDto.toAnimeSeasonal(): AnimeSeasonal {
	return AnimeSeasonal(
		data = data.map { it.toData() },
		paging = paging.toPaging(),
		season = season.toSeason()
	)
}
