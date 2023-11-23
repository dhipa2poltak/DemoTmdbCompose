package com.dpfht.demotmdbcompose.feature_movies_by_genre.event_state

import androidx.paging.PagingData
import com.dpfht.demotmdbcompose.domain.entity.MovieEntity
import kotlinx.coroutines.flow.MutableStateFlow

data class UIState(
  val genreId: Int = -1,
  val isLoaded: Boolean = false,
  val isLoading: Boolean = false,
  val moviesState: MutableStateFlow<PagingData<MovieEntity>> = MutableStateFlow(value = PagingData.empty()),
  val errorMessage: String = ""
)
