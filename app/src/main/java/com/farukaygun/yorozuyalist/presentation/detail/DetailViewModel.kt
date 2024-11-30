package com.farukaygun.yorozuyalist.presentation.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.farukaygun.yorozuyalist.R
import com.farukaygun.yorozuyalist.domain.interfaces.MediaDetail
import com.farukaygun.yorozuyalist.domain.models.MyListStatus
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeDetail
import com.farukaygun.yorozuyalist.domain.models.manga.MangaDetail
import com.farukaygun.yorozuyalist.domain.use_case.AnimeUseCase
import com.farukaygun.yorozuyalist.domain.use_case.MangaUseCase
import com.farukaygun.yorozuyalist.presentation.base.BaseViewModel
import com.farukaygun.yorozuyalist.util.Resource
import com.farukaygun.yorozuyalist.util.StringValue
import com.farukaygun.yorozuyalist.util.enums.ScreenType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class DetailViewModel(
	private val animeUseCase: AnimeUseCase,
	private val mangaUseCase: MangaUseCase,
	savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailState>() {
	override val _state = mutableStateOf(DetailState())

	init {
		savedStateHandle.get<String>("SCREEN_TYPE_PARAM")?.let { type ->
			_state.value = state.value.copy(type = ScreenType.valueOf(type))
		}

		savedStateHandle.get<String>("MEDIA_ID_PARAM")?.let { id ->
			onEvent(DetailEvent.InitRequestChain(id))
		}
	}

	@Suppress("UNCHECKED_CAST")
	private fun getDetail(id: String) {
		val getDetail = when (_state.value.type) {
			ScreenType.ANIME -> animeUseCase.executeGetAnimeDetail(id = id)
			ScreenType.MANGA -> mangaUseCase.executeGetMangaDetail(id = id)
		} as Flow<Resource<MediaDetail>>

		jobs += getDetail
			.flowOn(Dispatchers.IO)
			.handleResource(
				onSuccess = { detail ->
					_state.value = _state.value.copy(
						detail = detail,
						isLoading = false,
						error = ""
					)
				},
				onError = { error ->
					_state.value = _state.value.copy(
						error = error
							?: StringValue.StringResource(R.string.error_fetching).toString(),
						isLoading = false
					)
				},
				onLoading = {
					_state.value = _state.value.copy(isLoading = true)
				}
			)
	}

	private fun onMyListStatusChanged(myListStatus: MyListStatus?, isRemoved: Boolean) {
		_state.value = when (_state.value.detail) {
			is AnimeDetail ->
				_state.value.copy(
					detail = (_state.value.detail as? AnimeDetail)?.copy(
						myListStatus = myListStatus.takeIf { !isRemoved }
					)
				)

			is MangaDetail ->
				_state.value.copy(
					detail = (_state.value.detail as? MangaDetail)?.copy(
						myListStatus = myListStatus.takeIf { !isRemoved }
					)
				)

			else -> _state.value
		}
	}

	fun onEvent(event: DetailEvent) {
		when (event) {
			is DetailEvent.InitRequestChain -> getDetail(event.id)
			is DetailEvent.OnMyListStatusChanged -> onMyListStatusChanged(
				event.status,
				event.isRemoved
			)
		}
	}
}