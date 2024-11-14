package com.farukaygun.yorozuyalist.domain.models.enums

import com.farukaygun.yorozuyalist.domain.interfaces.Formattable
import com.google.gson.annotations.SerializedName

enum class MediaType(private val displayName: String) : Formattable {
	@SerializedName("unknown")
	UNKNOWN("Unknown"),

	@SerializedName("tv")
	TV("TV"),

	@SerializedName("ova")
	OVA("OVA"),

	@SerializedName("movie")
	MOVIE("Movie"),

	@SerializedName("special")
	SPECIAL("Special"),

	@SerializedName("ona")
	ONA("ONA"),

	@SerializedName("music")
	MUSIC("Music"),

	@SerializedName("manga")
	MANGA("Manga"),

	@SerializedName("novel")
	NOVEL("Novel"),

	@SerializedName("light_novel")
	LIGHT_NOVEL("Light Novel"),

	@SerializedName("one_shot")
	ONE_SHOT("One Shot"),

	@SerializedName("doujinshi")
	DOUJINSHI("Doujinshi"),

	@SerializedName("manhwa")
	MANHWA("Manhwa"),

	@SerializedName("manhua")
	MANHUA("Manhua"),

	@SerializedName("oel")
	OEL("Oel");

	override fun format() = displayName
}