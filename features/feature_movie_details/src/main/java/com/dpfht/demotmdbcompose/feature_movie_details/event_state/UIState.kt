package com.dpfht.demotmdbcompose.feature_movie_details.event_state

data class UIState(
  val movieId: Int = -1,
  val isLoaded: Boolean = false,
  val isLoading: Boolean = false,
  val errorMessage: String = "",
  val title: String = "",
  val imageUrl: String = "",
  val description: String = ""
)
