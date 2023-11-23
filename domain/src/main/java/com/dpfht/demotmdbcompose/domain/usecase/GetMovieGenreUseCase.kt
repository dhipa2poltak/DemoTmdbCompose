package com.dpfht.demotmdbcompose.domain.usecase

import com.dpfht.demotmdbcompose.domain.entity.GenreDomain
import com.dpfht.demotmdbcompose.domain.entity.Result

interface GetMovieGenreUseCase {

  suspend operator fun invoke(): Result<GenreDomain>
}
