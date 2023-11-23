package com.dpfht.demotmdbcompose.feature_movie_reviews.event_state

import androidx.paging.PagingData
import com.dpfht.demotmdbcompose.domain.entity.ReviewEntity
import kotlinx.coroutines.flow.MutableStateFlow

data class UIState(
  val movieId: Int = -1,
  val isLoaded: Boolean = false,
  val isLoading: Boolean = false,
  val reviewState: MutableStateFlow<PagingData<ReviewEntity>> = MutableStateFlow(value = PagingData.empty()),
  val errorMessage: String = ""
)
