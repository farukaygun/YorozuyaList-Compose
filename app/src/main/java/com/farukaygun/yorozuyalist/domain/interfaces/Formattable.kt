package com.farukaygun.yorozuyalist.domain.interfaces

fun interface Formattable {
	fun format(): String
	fun formatForApi(): String? = null
}