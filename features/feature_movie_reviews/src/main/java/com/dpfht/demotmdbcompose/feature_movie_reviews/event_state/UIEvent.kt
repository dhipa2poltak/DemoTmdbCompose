package com.dpfht.demotmdbcompose.feature_movie_reviews.event_state

sealed class UIEvent {
  data class Init(val movieId: Int): UIEvent()
  object OnBackPressed: UIEvent()
}
