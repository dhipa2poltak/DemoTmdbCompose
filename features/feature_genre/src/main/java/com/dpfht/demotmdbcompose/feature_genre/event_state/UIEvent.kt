package com.dpfht.demotmdbcompose.feature_genre.event_state

sealed class UIEvent {
  object Init: UIEvent()
  data class OnClickGenreItem(val genreId: Int, val genreName: String): UIEvent()
  object Refresh: UIEvent()
}
