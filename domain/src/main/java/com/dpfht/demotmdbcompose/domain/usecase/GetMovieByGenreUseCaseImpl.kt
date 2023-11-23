package com.dpfht.demotmdbcompose.domain.usecase

import com.dpfht.demotmdbcompose.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.demotmdbcompose.domain.entity.Result
import com.dpfht.demotmdbcompose.domain.repository.AppRepository

class GetMovieByGenreUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieByGenreUseCase {

  override suspend operator fun invoke(
    genreId: Int,
    page: Int
  ): Result<DiscoverMovieByGenreDomain> {
    return try {
      Result.Success(appRepository.getMoviesByGenre(genreId.toString(), page))
    } catch (e: Exception) {
      Result.Error(e.message ?: "")
    }
  }
}
