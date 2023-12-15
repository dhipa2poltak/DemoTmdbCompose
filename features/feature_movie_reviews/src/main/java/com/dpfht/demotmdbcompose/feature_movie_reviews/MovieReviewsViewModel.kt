package com.dpfht.demotmdbcompose.feature_movie_reviews

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dpfht.demotmdbcompose.feature_movie_reviews.event_state.UIEvent
import com.dpfht.demotmdbcompose.feature_movie_reviews.event_state.UIState
import com.dpfht.demotmdbcompose.feature_movie_reviews.paging.MovieReviewsDataSource
import com.dpfht.demotmdbcompose.framework.navigation.NavigationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieReviewsViewModel @Inject constructor(
  private val navigationService: NavigationService,
  private val movieReviewsDataSource: MovieReviewsDataSource,
): ViewModel() {

  private var _uiState = mutableStateOf(UIState())
  val uiState: State<UIState> = _uiState

  fun onEvent(event: UIEvent) {
    when (event) {
      is UIEvent.Init -> {
        _uiState.value = _uiState.value.copy(movieId = event.movieId)
        start()
      }
      UIEvent.OnBackPressed -> {
        navigationService.navigateUp()
      }
      UIEvent.Refresh -> {
        _uiState.value = _uiState.value.copy(
          isLoaded = false,
          isLoading = false,
          reviewState = MutableStateFlow(value = PagingData.empty()),
          errorMessage = ""
        )
        start()
      }
    }
  }

  private fun start() {
    if (_uiState.value.movieId != -1 && !_uiState.value.isLoaded) {
      loadReviews(_uiState.value.movieId)
    }
  }

  private fun loadReviews(movieId: Int) {
    movieReviewsDataSource.movieId = movieId
    movieReviewsDataSource.uiState = _uiState

    val pager = Pager(PagingConfig(pageSize = 20)) {
      movieReviewsDataSource
    }.flow.cachedIn(viewModelScope)

    viewModelScope.launch {
      pager.collect {
        _uiState.value = _uiState.value.copy(isLoaded = true)
        _uiState.value.reviewState.value = it
      }
    }
  }

  fun showErrorMessage(message: String) {
    navigationService.navigateToErrorMessage(message)
  }
}
