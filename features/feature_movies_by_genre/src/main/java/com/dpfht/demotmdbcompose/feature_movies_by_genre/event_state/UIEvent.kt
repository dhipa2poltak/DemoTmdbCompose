package com.dpfht.demotmdbcompose.feature_movies_by_genre.event_state

sealed class UIEvent {
  data class Init(val genreId: Int): UIEvent()
  data class OnClickMovieItem(val movieId: Int): UIEvent()
  object OnBackPressed: UIEvent()
}
