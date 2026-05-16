package com.farukaygun.yorozuyalist.presentation.detail

import androidx.lifecycle.SavedStateHandle
import com.farukaygun.yorozuyalist.domain.models.MyListStatus
import com.farukaygun.yorozuyalist.domain.models.anime.AnimeDetail
import com.farukaygun.yorozuyalist.domain.models.manga.MangaDetail
import com.farukaygun.yorozuyalist.domain.use_case.anime.GetAnimeDetailUseCase
import com.farukaygun.yorozuyalist.domain.use_case.manga.GetMangaDetailUseCase
import com.farukaygun.yorozuyalist.presentation.base.BaseViewModel
import com.farukaygun.yorozuyalist.util.enums.ScreenType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn

class DetailViewModel(
	private val getAnimeDetail: GetAnimeDetailUseCase,
	private val getMangaDetail: GetMangaDetailUseCase,
	savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailState>() {
	override val _state = MutableStateFlow(DetailState())

	init {
		savedStateHandle.get<String>("SCREEN_TYPE_PARAM")?.let { type ->
			_state.value = state.value.copy(type = ScreenType.valueOf(type))
		}

		savedStateHandle.get<String>("MEDIA_ID_PARAM")?.let { id ->
			onEvent(DetailEvent.InitRequestChain(id))
		}
	}

	private fun getDetail(id: String) {
		jobs += when (_state.value.type) {
			ScreenType.ANIME -> getAnimeDetail(id = id)
				.flowOn(Dispatchers.IO)
				.handleResource(
					onSuccess = { detail ->
						_state.value = _state.value.copy(detail = detail, isLoading = false, error = null)
					},
					onError = { error ->
						_state.value = _state.value.copy(error = error, isLoading = false)
					},
					onLoading = { _state.value = _state.value.copy(isLoading = true) }
				)
			ScreenType.MANGA -> getMangaDetail(id = id)
				.flowOn(Dispatchers.IO)
				.handleResource(
					onSuccess = { detail ->
						_state.value = _state.value.copy(detail = detail, isLoading = false, error = null)
					},
					onError = { error ->
						_state.value = _state.value.copy(error = error, isLoading = false)
					},
					onLoading = { _state.value = _state.value.copy(isLoading = true) }
				)
		}
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
