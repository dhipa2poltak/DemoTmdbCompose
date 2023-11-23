package com.dpfht.demotmdbcompose.domain.usecase

import com.dpfht.demotmdbcompose.domain.entity.ReviewDomain
import com.dpfht.demotmdbcompose.domain.entity.Result
import com.dpfht.demotmdbcompose.domain.repository.AppRepository

class GetMovieReviewUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieReviewUseCase {

  override suspend operator fun invoke(
    movieId: Int,
    page: Int
  ): Result<ReviewDomain> {
    return try {
      Result.Success(appRepository.getMovieReviews(movieId, page))
    } catch (e: Exception) {
      Result.Error(e.message ?: "")
    }
  }
}
