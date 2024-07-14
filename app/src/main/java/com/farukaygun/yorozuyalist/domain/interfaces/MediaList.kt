package com.farukaygun.yorozuyalist.domain.interfaces

import com.farukaygun.yorozuyalist.domain.model.Data
import com.farukaygun.yorozuyalist.domain.model.Paging

interface MediaList {
	val data: List<Data>
	val paging: Paging
}