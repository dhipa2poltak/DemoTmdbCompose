package com.dpfht.demotmdbcompose.domain.entity

sealed class Result<out T> {
  data class Success<out T>(val value: T): Result<T>()
  data class Error(val message: String): Result<Nothing>()
}
