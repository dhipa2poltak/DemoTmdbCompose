package com.dpfht.demotmdbcompose.domain.usecase

import com.dpfht.demotmdbcompose.domain.entity.TrailerDomain
import com.dpfht.demotmdbcompose.domain.entity.Result

interface GetMovieTrailerUseCase {

  suspend operator fun invoke(
    movieId: Int
  ): Result<TrailerDomain>
}
