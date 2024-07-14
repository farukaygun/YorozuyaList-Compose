package com.farukaygun.yorozuyalist.data.remote.dto.anime

import com.farukaygun.yorozuyalist.domain.model.Data
import com.farukaygun.yorozuyalist.domain.model.Paging
import com.farukaygun.yorozuyalist.domain.model.anime.AnimeSeasonal
import com.farukaygun.yorozuyalist.domain.model.anime.Season
import com.google.gson.annotations.SerializedName

data class AnimeSeasonalDto(
	@SerializedName("data")
	var data: List<Data>,
	@SerializedName("paging")
	val paging: Paging,
	@SerializedName("season")
	val season: Season,
)

fun AnimeSeasonalDto.toAnimeSeasonal(): AnimeSeasonal {
	return AnimeSeasonal(
		data = data,
		paging = paging,
		season = season
	)
}
