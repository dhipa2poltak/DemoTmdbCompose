package com.dpfht.demotmdbcompose.feature_movies_by_genre

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dpfht.demotmdbcompose.feature_movies_by_genre.event_state.UIEvent
import com.dpfht.demotmdbcompose.feature_movies_by_genre.event_state.UIState
import com.dpfht.demotmdbcompose.feature_movies_by_genre.paging.MoviesByGenreDataSource
import com.dpfht.demotmdbcompose.framework.navigation.NavigationService
import com.dpfht.demotmdbcompose.framework.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesByGenreViewModel @Inject constructor(
  private val navigationService: NavigationService,
  private val moviesByGenreDataSource: MoviesByGenreDataSource,
): ViewModel() {

  private var _uiState = mutableStateOf(UIState())
  val uiState: State<UIState> = _uiState

  fun onEvent(event: UIEvent) {
    when (event) {
      is UIEvent.Init -> {
        _uiState.value = _uiState.value.copy(genreId = event.genreId)
        start()
      }
      is UIEvent.OnClickMovieItem -> {
        navigationService.navigate(Screen.MovieDetails(event.movieId))
      }
      UIEvent.OnBackPressed -> {
        navigationService.navigateUp()
      }
      UIEvent.Refresh -> {
        _uiState.value = _uiState.value.copy(
          isLoaded = false,
          isLoading = false,
          moviesState = MutableStateFlow(value = PagingData.empty()),
          errorMessage = ""
        )
        start()
      }
    }
  }

  private fun start() {
    if (_uiState.value.genreId != -1 && !_uiState.value.isLoaded) {
      loadMoviesByGenre(_uiState.value.genreId)
    }
  }

  private fun loadMoviesByGenre(genreId: Int) {
    moviesByGenreDataSource.genreId = genreId
    moviesByGenreDataSource.uiState = _uiState

    val pager = Pager(PagingConfig(pageSize = 20)) {
      moviesByGenreDataSource
    }.flow.cachedIn(viewModelScope)

    viewModelScope.launch {
      pager.collect {
        _uiState.value = _uiState.value.copy(isLoaded = true)
        _uiState.value.moviesState.value = it
      }
    }
  }

  fun showErrorMessage(message: String) {
    navigationService.navigateToErrorMessage(message)
  }
}
