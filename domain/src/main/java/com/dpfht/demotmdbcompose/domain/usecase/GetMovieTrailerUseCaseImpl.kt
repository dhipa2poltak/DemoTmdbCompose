package com.dpfht.demotmdbcompose.domain.usecase

import com.dpfht.demotmdbcompose.domain.entity.TrailerDomain
import com.dpfht.demotmdbcompose.domain.entity.Result
import com.dpfht.demotmdbcompose.domain.repository.AppRepository

class GetMovieTrailerUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieTrailerUseCase {

  override suspend operator fun invoke(
    movieId: Int
  ): Result<TrailerDomain> {
    return try {
      Result.Success(appRepository.getMovieTrailer(movieId))
    } catch (e: Exception) {
      Result.Error(e.message ?: "")
    }
  }
}
