package com.dpfht.demotmdbcompose.framework.data.datasource.remote

import android.content.Context
import com.dpfht.demotmdbcompose.data.datasource.RemoteDataSource
import com.dpfht.demotmdbcompose.data.model.remote.response.ErrorResponse
import com.dpfht.demotmdbcompose.data.model.remote.response.toDomain
import com.dpfht.demotmdbcompose.domain.entity.DiscoverMovieByGenreDomain
import com.dpfht.demotmdbcompose.domain.entity.GenreDomain
import com.dpfht.demotmdbcompose.domain.entity.MovieDetailsDomain
import com.dpfht.demotmdbcompose.domain.entity.ReviewDomain
import com.dpfht.demotmdbcompose.domain.entity.TrailerDomain
import com.dpfht.demotmdbcompose.framework.R
import com.dpfht.demotmdbcompose.framework.data.datasource.remote.rest.RestService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.nio.charset.Charset

class RemoteDataSourceImpl(
  private val context: Context,
  private val restService: RestService
):
  RemoteDataSource {

  override suspend fun getMovieGenre(): GenreDomain {
    return safeApiCall(Dispatchers.IO) { restService.getMovieGenre() }.toDomain()
  }

  override suspend fun getMoviesByGenre(genreId: String, page: Int): DiscoverMovieByGenreDomain {
    return safeApiCall(Dispatchers.IO) { restService.getMoviesByGenre(genreId, page) }.toDomain()
  }

  override suspend fun getMovieDetail(movieId: Int): MovieDetailsDomain {
    return safeApiCall(Dispatchers.IO) { restService.getMovieDetail(movieId) }.toDomain()
  }

  override suspend fun getMovieReviews(movieId: Int, page: Int): ReviewDomain {
    return safeApiCall(Dispatchers.IO) { restService.getMovieReviews(movieId, page) }.toDomain()
  }

  override suspend fun getMovieTrailer(movieId: Int): TrailerDomain {
    return safeApiCall(Dispatchers.IO) { restService.getMovieTrailers(movieId) }.toDomain()
  }

  private suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): T {
    return withContext(dispatcher) {
      try {
        apiCall.invoke()
      } catch (t: Throwable) {
        throw when (t) {
          is IOException -> Exception(context.getString(R.string.framework_text_error_connection))
          is HttpException -> {
            //val code = t.code()
            val errorResponse = convertErrorBody(t)

            Exception(errorResponse?.statusMessage ?: context.getString(R.string.framework_text_error_http))
          }
          else -> {
            Exception(context.getString(R.string.framework_text_error_conversion))
          }
        }
      }
    }
  }

  private fun convertErrorBody(t: HttpException): ErrorResponse? {
    return try {
      t.response()?.errorBody()?.source().let {
        val json = it?.readString(Charset.defaultCharset())
        val typeToken = object : TypeToken<ErrorResponse>() {}.type
        val errorResponse = Gson().fromJson<ErrorResponse>(json, typeToken)
        errorResponse
      }
    } catch (e: Exception) {
      null
    }
  }
}
