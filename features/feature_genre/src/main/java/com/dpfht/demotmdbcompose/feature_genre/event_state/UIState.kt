package com.dpfht.demotmdbcompose.feature_genre.event_state

import com.dpfht.demotmdbcompose.domain.entity.GenreEntity

data class UIState(
  val isLoading: Boolean = false,
  val genres: List<GenreEntity> = arrayListOf(),
  val errorMessage: String = ""
)
