package com.farukaygun.yorozuyalist.domain.interfaces

import com.farukaygun.yorozuyalist.domain.models.Data
import com.farukaygun.yorozuyalist.domain.models.Paging

interface MediaList {
	val data: List<Data>
	val paging: Paging
}