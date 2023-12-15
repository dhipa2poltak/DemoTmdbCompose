package com.dpfht.demotmdbcompose.feature_genre

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.demotmdbcompose.domain.entity.GenreEntity
import com.dpfht.demotmdbcompose.domain.entity.Result.Error
import com.dpfht.demotmdbcompose.domain.entity.Result.Success
import com.dpfht.demotmdbcompose.domain.usecase.GetMovieGenreUseCase
import com.dpfht.demotmdbcompose.feature_genre.event_state.UIEvent
import com.dpfht.demotmdbcompose.feature_genre.event_state.UIState
import com.dpfht.demotmdbcompose.framework.navigation.NavigationService
import com.dpfht.demotmdbcompose.framework.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
  private val navigationService: NavigationService,
  private val getMovieGenreUseCase: GetMovieGenreUseCase,
): ViewModel() {

  private var _uiState = mutableStateOf(UIState())
  val uiState: State<UIState> = _uiState

  fun onEvent(event: UIEvent) {
    when (event) {
      UIEvent.Init -> {
        start()
      }
      is UIEvent.OnClickGenreItem -> {
        navigationService.navigate(Screen.MoviesByGenre(event.genreId, event.genreName))
      }
      UIEvent.Refresh -> {
        _uiState.value = _uiState.value.copy(isLoading = false, genres = arrayListOf(), errorMessage = "")
        start()
      }
    }
  }

  private fun start() {
    if (_uiState.value.genres.isNotEmpty()) {
      return
    }

    loadGenres()
  }

  private fun loadGenres() {
    viewModelScope.launch {
      _uiState.value = _uiState.value.copy(isLoading = true)
      when (val result = getMovieGenreUseCase()) {
        is Success -> {
          onSuccessLoadGenres(result.value.genres)
        }
        is Error -> {
          onErrorLoadGenres(result.message)
        }
      }
    }
  }

  private fun onSuccessLoadGenres(genres: List<GenreEntity>) {
    _uiState.value = _uiState.value.copy(
      isLoading = false,
      genres = genres
    )
  }

  private fun onErrorLoadGenres(message: String) {
    _uiState.value = _uiState.value.copy(
      isLoading = false,
      errorMessage = message
    )
    navigationService.navigateToErrorMessage(message)
  }
}
