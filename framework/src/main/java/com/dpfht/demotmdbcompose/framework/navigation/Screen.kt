package com.dpfht.demotmdbcompose.framework.navigation

sealed class Screen(val route: String) {
  object IntroBaseRoute: Screen("intro")
  object MainBaseRoute: Screen("main")

  object None: Screen("")
  object NavigateUp: Screen("navigateUp")

  object Splash: Screen("splash")
  object Genre: Screen("genre")
  data class MoviesByGenre(val genreId: Int, val genreName: String) : Screen("") {
    companion object {
      const val route = "movies_by_genre/{genreId}/{genreName}"
    }

    fun createRoute() = "movies_by_genre/$genreId/$genreName"
  }
  data class MovieDetails(val movieId: Int) : Screen("") {
    companion object {
      const val route = "movie_details/{movieId}"
    }

    fun createRoute() = "movie_details/$movieId"
  }
  data class MovieReviews(val movieId: Int, val movieTitle: String) : Screen("") {
    companion object {
      const val route = "movie_reviews/{movieId}/{movieTitle}"
    }

    fun createRoute() = "movie_reviews/$movieId/$movieTitle"
  }

  data class MovieTrailer(val movieId: Int): Screen("")
  data class Error(val message: String): Screen("")
}
