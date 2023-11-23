package com.dpfht.demotmdbcompose.feature_movie_trailer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dpfht.demotmdbcompose.domain.entity.Result
import com.dpfht.demotmdbcompose.domain.entity.TrailerEntity
import com.dpfht.demotmdbcompose.domain.usecase.GetMovieTrailerUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Locale

class MovieTrailerViewModel(
  val getMovieTrailerUseCase: GetMovieTrailerUseCase,
  private val scope: CoroutineScope
) {

  private var _movieId = -1

  private val _keyVideo = MutableLiveData<String>()
  val keyVideo: LiveData<String> = _keyVideo

  private val mErrorMessage = MutableLiveData<String>()
  val errorMessage: LiveData<String> = mErrorMessage

  private val mShowCanceledMessage = MutableLiveData<Boolean>()
  val showCanceledMessage: LiveData<Boolean> = mShowCanceledMessage

  fun setMovieId(movieId: Int) {
    this._movieId = movieId
  }

  fun start() {
    if (_movieId != -1) {
      getMovieTrailer()
    }
  }

  private fun getMovieTrailer() {
    scope.launch {
      when (val result = getMovieTrailerUseCase(_movieId)) {
        is Result.Success -> {
          onSuccess(result.value.results)
        }
        is Result.Error -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(trailers: List<TrailerEntity>) {
    scope.launch(Dispatchers.Main) {
      var keyVideo = ""
      for (trailer in trailers) {
        if (trailer.site.lowercase(Locale.ROOT).trim() == "youtube"
        ) {
          keyVideo = trailer.key
          break
        }
      }

      if (keyVideo.isNotEmpty()) {
        _keyVideo.value = keyVideo
      }
    }
  }

  private fun onError(message: String) {
    scope.launch(Dispatchers.Main) {
      mErrorMessage.value = message
    }
  }

  fun onDestroy() {
    if (scope.isActive) {
      scope.cancel()
    }
  }
}
