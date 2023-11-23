package com.dpfht.demotmdbcompose.domain.repository

import com.dpfht.demotmdbcompose.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.demotmdbcompose.domain.entity.GenreDomain
import com.dpfht.demotmdbcompose.domain.entity.MovieDetailsDomain
import com.dpfht.demotmdbcompose.domain.entity.ReviewDomain
import com.dpfht.demotmdbcompose.domain.entity.TrailerDomain

interface AppRepository {

  suspend fun getMovieGenre(): GenreDomain

  suspend fun getMoviesByGenre(genreId: String, page: Int): DiscoverMovieByGenreDomain

  suspend fun getMovieDetail(movieId: Int): MovieDetailsDomain

  suspend fun getMovieReviews(movieId: Int, page: Int): ReviewDomain

  suspend fun getMovieTrailer(movieId: Int): TrailerDomain
}
