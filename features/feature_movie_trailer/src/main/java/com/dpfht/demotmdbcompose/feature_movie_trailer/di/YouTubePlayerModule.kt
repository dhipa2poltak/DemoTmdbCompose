package com.dpfht.demotmdbcompose.feature_movie_trailer.di

import com.dpfht.demotmdbcompose.domain.repository.AppRepository
import com.dpfht.demotmdbcompose.domain.usecase.GetMovieTrailerUseCase
import com.dpfht.demotmdbcompose.domain.usecase.GetMovieTrailerUseCaseImpl
import com.dpfht.demotmdbcompose.feature_movie_trailer.MovieTrailerViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

@Module
@InstallIn(SingletonComponent::class)
class YouTubePlayerModule {

  @Provides
  fun provideJob(): Job {
    return Job()
  }

  @Provides
  fun provideCoroutineScope(job: Job): CoroutineScope {
    return CoroutineScope(job)
  }

  @Provides
  fun provideGetMovieTrailerUseCase(appRepository: AppRepository): GetMovieTrailerUseCase {
    return GetMovieTrailerUseCaseImpl(appRepository)
  }

  @Provides
  fun provideYouTubePlayerViewModel(
    useCase: GetMovieTrailerUseCase,
    scope: CoroutineScope
  ): MovieTrailerViewModel {
    return MovieTrailerViewModel(useCase, scope)
  }
}
