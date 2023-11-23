package com.dpfht.demotmdbcompose.domain.usecase

import com.dpfht.demotmdbcompose.domain.entity.GenreDomain
import com.dpfht.demotmdbcompose.domain.entity.Result
import com.dpfht.demotmdbcompose.domain.repository.AppRepository

class GetMovieGenreUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieGenreUseCase {

  override suspend operator fun invoke(): Result<GenreDomain> {
    return try {
      Result.Success(appRepository.getMovieGenre())
    } catch (e: Exception) {
      Result.Error(e.message ?: "")
    }
  }
}
