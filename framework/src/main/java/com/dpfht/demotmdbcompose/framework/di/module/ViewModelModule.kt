package com.dpfht.demotmdbcompose.framework.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import com.dpfht.demotmdbcompose.domain.repository.AppRepository
import com.dpfht.demotmdbcompose.domain.usecase.GetMovieByGenreUseCase
import com.dpfht.demotmdbcompose.domain.usecase.GetMovieByGenreUseCaseImpl
import com.dpfht.demotmdbcompose.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.demotmdbcompose.domain.usecase.GetMovieDetailsUseCaseImpl
import com.dpfht.demotmdbcompose.domain.usecase.GetMovieGenreUseCase
import com.dpfht.demotmdbcompose.domain.usecase.GetMovieGenreUseCaseImpl
import com.dpfht.demotmdbcompose.domain.usecase.GetMovieReviewUseCase
import com.dpfht.demotmdbcompose.domain.usecase.GetMovieReviewUseCaseImpl
import com.dpfht.demotmdbcompose.domain.usecase.GetMovieTrailerUseCase
import com.dpfht.demotmdbcompose.domain.usecase.GetMovieTrailerUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

  @Provides
  fun provideGetMovieByGenreUseCase(appRepository: AppRepository): GetMovieByGenreUseCase {
    return GetMovieByGenreUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetMovieDetailsUseCase(appRepository: AppRepository): GetMovieDetailsUseCase {
    return GetMovieDetailsUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetMovieGenreUseCase(appRepository: AppRepository): GetMovieGenreUseCase {
    return GetMovieGenreUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetMovieReviewUseCase(appRepository: AppRepository): GetMovieReviewUseCase {
    return GetMovieReviewUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetMovieTrailerUseCase(appRepository: AppRepository): GetMovieTrailerUseCase {
    return GetMovieTrailerUseCaseImpl(appRepository)
  }
}
