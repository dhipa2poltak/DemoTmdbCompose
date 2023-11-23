package com.dpfht.demotmdbcompose.feature_movie_reviews.paging

import androidx.compose.runtime.MutableState
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dpfht.demotmdbcompose.domain.entity.Result
import com.dpfht.demotmdbcompose.domain.entity.ReviewEntity
import com.dpfht.demotmdbcompose.domain.usecase.GetMovieReviewUseCase
import com.dpfht.demotmdbcompose.feature_movie_reviews.event_state.UIState
import javax.inject.Inject

class MovieReviewsDataSource @Inject constructor(
  private val getMovieReviewUseCase: GetMovieReviewUseCase,
) : PagingSource<Int, ReviewEntity>() {

  lateinit var uiState: MutableState<UIState>
  var movieId: Int? = null

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewEntity> {
    try {
      val currentLoadingPageKey = params.key ?: 1
      val arrList = arrayListOf<ReviewEntity>()
      var prevKey: Int? = null
      var nextKey: Int? = null

      uiState.value = uiState.value.copy(isLoading = true, errorMessage = "")

      movieId?.let {
        when (val result = getMovieReviewUseCase(it, currentLoadingPageKey)) {
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

  override fun getRefreshKey(state: PagingState<Int, ReviewEntity>): Int? {
    return state.anchorPosition
  }
}
