package com.dpfht.demotmdbcompose.feature_movie_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.demotmdbcompose.domain.entity.MovieDetailsDomain
import com.dpfht.demotmdbcompose.domain.entity.Result
import com.dpfht.demotmdbcompose.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.demotmdbcompose.feature_movie_details.event_state.UIEvent
import com.dpfht.demotmdbcompose.feature_movie_details.event_state.UIState
import com.dpfht.demotmdbcompose.framework.navigation.NavigationService
import com.dpfht.demotmdbcompose.framework.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
  private val navigationService: NavigationService,
  private val getMovieDetailsUseCase: GetMovieDetailsUseCase
): ViewModel() {

  private var _uiState = mutableStateOf(UIState())
  val uiState: State<UIState> = _uiState

  fun onEvent(event: UIEvent) {
    when (event) {
      is UIEvent.Init -> {
        _uiState.value = _uiState.value.copy(movieId = event.movieId)
        start()
      }
      is UIEvent.OnClickShowReview -> {
        navigationService.navigate(Screen.MovieReviews(event.movieId, _uiState.value.title))
      }
      is UIEvent.OnClickShowTrailer -> {
        navigationService.navigate(Screen.MovieTrailer(event.movieId))
      }
      UIEvent.OnBackPressed -> {
        navigationService.navigateUp()
      }
    }
  }

  private fun start() {
    if (_uiState.value.movieId != -1 && !_uiState.value.isLoaded) {
      loadMovieDetails(_uiState.value.movieId)
    }
  }

  private fun loadMovieDetails(movieId: Int) {
    _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = "")
    viewModelScope.launch {
      when (val result = getMovieDetailsUseCase(movieId)) {
        is Result.Success -> {
          onSuccessLoadMovieDetails(result.value)
        }
        is Result.Error -> {
          onErrorLoadMovieDetails(result.message)
        }
      }
    }
  }

  private fun onSuccessLoadMovieDetails(movieDetails: MovieDetailsDomain) {
    _uiState.value = _uiState.value.copy(
      isLoading = false,
      errorMessage = "",
      isLoaded = true,
      title = movieDetails.title,
      imageUrl = movieDetails.imageUrl,
      description = movieDetails.overview
    )
  }

  private fun onErrorLoadMovieDetails(message: String) {
    _uiState.value = _uiState.value.copy(
      isLoading = false,
      errorMessage = message
    )
    navigationService.navigateToErrorMessage(message)
  }
}
