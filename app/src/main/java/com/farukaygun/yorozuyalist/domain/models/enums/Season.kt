package com.farukaygun.yorozuyalist.domain.models.enums

import com.google.gson.annotations.SerializedName

enum class Season(val displayName: String) {
	@SerializedName("winter")
	WINTER("Winter"),

	@SerializedName("spring")
	SPRING("Spring"),

	@SerializedName("summer")
	SUMMER("Summer"),

	@SerializedName("fall")
	FALL("Fall")
}