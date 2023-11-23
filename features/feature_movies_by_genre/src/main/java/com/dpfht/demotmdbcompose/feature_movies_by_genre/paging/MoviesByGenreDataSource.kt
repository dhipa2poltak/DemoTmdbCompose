package com.dpfht.demotmdbcompose.feature_movies_by_genre.paging

import androidx.compose.runtime.MutableState
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dpfht.demotmdbcompose.domain.entity.MovieEntity
import com.dpfht.demotmdbcompose.domain.entity.Result
import com.dpfht.demotmdbcompose.domain.usecase.GetMovieByGenreUseCase
import com.dpfht.demotmdbcompose.feature_movies_by_genre.event_state.UIState
import javax.inject.Inject

class MoviesByGenreDataSource @Inject constructor(
  private val getMovieByGenreUseCase: GetMovieByGenreUseCase,
) : PagingSource<Int, MovieEntity>() {

  lateinit var uiState: MutableState<UIState>
  var genreId: Int? = null

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
    try {
      val currentLoadingPageKey = params.key ?: 1
      val arrList = arrayListOf<MovieEntity>()
      var prevKey: Int? = null
      var nextKey: Int? = null

      uiState.value = uiState.value.copy(isLoading = true, errorMessage = "")

      genreId?.let {
        when (val result = getMovieByGenreUseCase(it, currentLoadingPageKey)) {
          is Result.Success -> {
            arrList.addAll(result.value.results)
            prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            nextKey = if (result.value.results.isEmpty()) null else currentLoadingPageKey + 1
          }
          is Result.Error -> {
            throw Exception(result.message)
          }
        }
      }

      uiState.value = uiState.value.copy(isLoading = false)

      return LoadResult.Page(
        data = arrList,
        prevKey = prevKey,
        nextKey = nextKey
      )
    } catch (e: Exception) {
      uiState.value = uiState.value.copy(isLoading = false, errorMessage = e.message ?: "")

      return LoadResult.Error(e)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
    return state.anchorPosition
  }
}
