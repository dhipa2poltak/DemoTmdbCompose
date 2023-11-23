package com.dpfht.demotmdbcompose.domain.usecase

import com.dpfht.demotmdbcompose.domain.entity.ReviewDomain
import com.dpfht.demotmdbcompose.domain.entity.Result

interface GetMovieReviewUseCase {

  suspend operator fun invoke(
    movieId: Int,
    page: Int
  ): Result<ReviewDomain>
}
