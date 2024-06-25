package com.farukaygun.yorozuyalist.presentation.search

sealed class SearchEvent {
	data class Search(val query: String) : SearchEvent()
	data object LoadMore : SearchEvent()
}