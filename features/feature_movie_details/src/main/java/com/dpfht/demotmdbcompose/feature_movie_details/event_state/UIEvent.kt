package com.dpfht.demotmdbcompose.feature_movie_details.event_state

sealed class UIEvent {
  data class Init(val movieId: Int): UIEvent()
  data class OnClickShowReview(val movieId: Int): UIEvent()
  data class OnClickShowTrailer(val movieId: Int): UIEvent()
  object OnBackPressed: UIEvent()
}
