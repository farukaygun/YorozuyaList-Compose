package com.farukaygun.yorozuyalist.domain.models.enums

import com.farukaygun.yorozuyalist.domain.interfaces.Formattable
import com.google.gson.annotations.SerializedName

enum class MyListMediaStatus(private val displayName: String, private val apiName: String) :
	Formattable {
	@SerializedName("watching")
	WATCHING("Watching", "watching"),

	@SerializedName("reading")
	READING("Reading", "reading"),

	@SerializedName("completed")
	COMPLETED("Completed", "completed"),

	@SerializedName("on_hold")
	ON_HOLD("On Hold", "on_hold"),

	@SerializedName("dropped")
	DROPPED("Dropped", "dropped"),

	@SerializedName("plan_to_watch")
	PLAN_TO_WATCH("Plan to Watch", "plan_to_watch"),

	@SerializedName("plan_to_read")
	PLAN_TO_READ("Plan to Read", "plan_to_read");

	override fun format() = displayName

	override fun formatForApi() = apiName
}