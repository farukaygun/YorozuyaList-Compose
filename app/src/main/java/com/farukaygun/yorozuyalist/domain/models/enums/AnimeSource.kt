package com.farukaygun.yorozuyalist.domain.models.enums

import com.google.gson.annotations.SerializedName

enum class AnimeSource(val displayName: String) {
	@SerializedName("original")
	ORIGINAL("Original"),
	@SerializedName("manga")
	MANGA("Manga"),
	@SerializedName("novel")
	NOVEL("Novel"),
	@SerializedName("light_novel")
	LIGHT_NOVEL("Light Novel"),
	@SerializedName("visual_novel")
	VISUAL_NOVEL("Visual Novel"),
	@SerializedName("game")
	GAME("Game"),
	@SerializedName("web_manga")
	WEB_MANGA("Web Manga"),
	@SerializedName("web_novel")
	WEB_NOVEL("Web Novel"),
	@SerializedName("music")
	MUSIC("Music"),
	@SerializedName("mixed_media")
	MIXED_MEDIA("Mixed Media"),
	@SerializedName("4_koma_manga")
	YON_KOMA_MANGA("4-Koma Manga")
}