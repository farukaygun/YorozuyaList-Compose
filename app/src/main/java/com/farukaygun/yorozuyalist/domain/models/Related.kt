package com.farukaygun.yorozuyalist.domain.models

data class Related(
	val node: Node?,
	val relationType: String,
	val relationTypeFormatted: String
)