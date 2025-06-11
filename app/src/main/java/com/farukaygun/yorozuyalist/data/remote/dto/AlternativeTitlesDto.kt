package com.farukaygun.yorozuyalist.data.remote.dto

import com.farukaygun.yorozuyalist.domain.models.AlternativeTitles
import com.google.gson.annotations.SerializedName

data class AlternativeTitlesDto(
	@SerializedName("synonyms")
	val synonyms: List<String>,
	@SerializedName("en")
	val en: String,
	@SerializedName("ja")
	val ja: String
)

fun AlternativeTitlesDto.toAlternativeTitles(): AlternativeTitles {
	return AlternativeTitles(
		synonyms = synonyms,
		en = en,
		ja = ja
	)
}