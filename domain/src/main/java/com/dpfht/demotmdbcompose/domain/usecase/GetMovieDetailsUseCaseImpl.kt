package com.dpfht.demotmdbcompose.domain.usecase

import com.dpfht.demotmdbcompose.domain.entity.MovieDetailsDomain
import com.dpfht.demotmdbcompose.domain.entity.Result
import com.dpfht.demotmdbcompose.domain.repository.AppRepository


class GetMovieDetailsUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieDetailsUseCase {

  override suspend operator fun invoke(
    movieId: Int
  ): Result<MovieDetailsDomain> {
    return try {
      Result.Success(appRepository.getMovieDetail(movieId))
    } catch (e: Exception) {
      Result.Error(e.message ?: "")
    }
  }
}
