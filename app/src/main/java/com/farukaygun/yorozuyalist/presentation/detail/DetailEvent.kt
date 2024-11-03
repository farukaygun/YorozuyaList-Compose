package com.farukaygun.yorozuyalist.presentation.detail

import com.farukaygun.yorozuyalist.domain.models.MyListStatus

sealed class DetailEvent {
	data class InitRequestChain(val id: String) : DetailEvent()
	data class OnMyListStatusChanged(val status: MyListStatus?, val isRemoved: Boolean) : DetailEvent()
}