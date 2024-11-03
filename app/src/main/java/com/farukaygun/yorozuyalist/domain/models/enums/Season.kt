package com.farukaygun.yorozuyalist.domain.models.enums

import com.farukaygun.yorozuyalist.domain.interfaces.Formattable
import com.google.gson.annotations.SerializedName

enum class Season(private val displayName: String) : Formattable {
	@SerializedName("winter")
	WINTER("Winter"),

	@SerializedName("spring")
	SPRING("Spring"),

	@SerializedName("summer")
	SUMMER("Summer"),

	@SerializedName("fall")
	FALL("Fall");

	override fun format() = displayName
}