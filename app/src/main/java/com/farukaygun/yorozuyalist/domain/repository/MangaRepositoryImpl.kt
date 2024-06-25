package com.farukaygun.yorozuyalist.domain.repository

import com.farukaygun.yorozuyalist.data.remote.APIService
import com.farukaygun.yorozuyalist.data.repository.MangaRepository

class MangaRepositoryImpl(
	private val api: APIService
) : MangaRepository {

}