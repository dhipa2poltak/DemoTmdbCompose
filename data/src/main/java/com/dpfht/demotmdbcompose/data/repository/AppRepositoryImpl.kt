package com.dpfht.demotmdbcompose.data.repository

import com.dpfht.demotmdbcompose.data.datasource.RemoteDataSource
import com.dpfht.demotmdbcompose.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.demotmdbcompose.domain.entity.GenreDomain
import com.dpfht.demotmdbcompose.domain.entity.MovieDetailsDomain
import com.dpfht.demotmdbcompose.domain.entity.ReviewDomain
import com.dpfht.demotmdbcompose.domain.entity.TrailerDomain
import com.dpfht.demotmdbcompose.domain.repository.AppRepository

class AppRepositoryImpl(private val remoteDataSource: RemoteDataSource): AppRepository {

  override suspend fun getMovieGenre(): GenreDomain {
    return remoteDataSource.getMovieGenre()
  }

  override suspend fun getMoviesByGenre(genreId: String, page: Int): DiscoverMovieByGenreDomain {
    return remoteDataSource.getMoviesByGenre(genreId, page)
  }

  override suspend fun getMovieDetail(movieId: Int): MovieDetailsDomain {
    return remoteDataSource.getMovieDetail(movieId)
  }

  override suspend fun getMovieReviews(movieId: Int, page: Int): ReviewDomain {
    return remoteDataSource.getMovieReviews(movieId, page)
  }

  override suspend fun getMovieTrailer(movieId: Int): TrailerDomain {
    return remoteDataSource.getMovieTrailer(movieId)
  }
}
