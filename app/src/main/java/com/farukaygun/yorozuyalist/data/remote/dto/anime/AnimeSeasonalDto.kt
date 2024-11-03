package com.farukaygun.yorozuyalist.data.remote.dto.anime

import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.Paging
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeSeasonal
import com.google.gson.annotations.SerializedName

data class AnimeSeasonalDto(
	@SerializedName("data")
	var data: List<Data>,
	@SerializedName("paging")
	val paging: Paging,
	@SerializedName("season")
	val season: SeasonDto,
)

fun AnimeSeasonalDto.toAnimeSeasonal(): AnimeSeasonal {
	return AnimeSeasonal(
		data = data,
		paging = paging,
		season = season.toSeason()
	)
}
