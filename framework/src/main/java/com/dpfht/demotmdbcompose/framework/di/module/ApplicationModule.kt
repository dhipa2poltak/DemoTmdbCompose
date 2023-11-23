package com.dpfht.demotmdbcompose.framework.di.module

import com.dpfht.demotmdbcompose.data.datasource.RemoteDataSource
import com.dpfht.demotmdbcompose.data.repository.AppRepositoryImpl
import com.dpfht.demotmdbcompose.domain.repository.AppRepository
import com.dpfht.demotmdbcompose.framework.navigation.NavigationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

  @Singleton
  @Provides
  fun providesNavigationService() = NavigationService()

  @Provides
  @Singleton
  fun provideAppRepository(remoteDataSource: RemoteDataSource): AppRepository {
    return AppRepositoryImpl(remoteDataSource)
  }
}
