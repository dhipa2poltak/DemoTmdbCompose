package com.dpfht.demotmdbcompose.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.demotmdbcompose.data.Constants
import com.dpfht.demotmdbcompose.data.model.remote.Movie
import com.dpfht.demotmdbcompose.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.demotmdbcompose.domain.entity.MovieEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
@Suppress("unused")
data class DiscoverMovieByGenreResponse(
  val page: Int? = -1,
  val results: List<Movie>? = listOf(),

  @SerializedName("total_pages")
  @Expose
  val totalPages: Int? = -1,

  @SerializedName("total_results")
  @Expose
  val totalResults: Int? = -1
)

fun DiscoverMovieByGenreResponse.toDomain(): DiscoverMovieByGenreDomain {
  val movieEntities = results?.map {
    MovieEntity(
      it.id ?: -1,
      it.title ?: "",
      it.overview ?: "",
      if (it.posterPath?.isNotEmpty() == true) Constants.IMAGE_URL_BASE_PATH + it.posterPath else ""
    )
  }

  return DiscoverMovieByGenreDomain(
    page ?: -1,
    movieEntities?.toList() ?: listOf(),
    totalPages ?: -1,
    totalResults ?: -1
  )
}
