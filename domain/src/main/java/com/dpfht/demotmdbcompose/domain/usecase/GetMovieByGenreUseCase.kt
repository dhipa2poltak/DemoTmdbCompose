package com.dpfht.demotmdbcompose.domain.usecase

import com.dpfht.demotmdbcompose.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.demotmdbcompose.domain.entity.Result

interface GetMovieByGenreUseCase {

  suspend operator fun invoke(
    genreId: Int,
    page: Int
  ): Result<DiscoverMovieByGenreDomain>
}
