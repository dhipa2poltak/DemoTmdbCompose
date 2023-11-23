package com.dpfht.demotmdbcompose.domain.usecase

import com.dpfht.demotmdbcompose.domain.entity.MovieDetailsDomain
import com.dpfht.demotmdbcompose.domain.entity.Result

interface GetMovieDetailsUseCase {

  suspend operator fun invoke(
    movieId: Int
  ): Result<MovieDetailsDomain>
}
