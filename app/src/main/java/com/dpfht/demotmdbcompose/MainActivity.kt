package com.dpfht.demotmdbcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.dpfht.demotmdbcompose.feature_error_message.AppBottomSheet
import com.dpfht.demotmdbcompose.feature_genre.GenreScreen
import com.dpfht.demotmdbcompose.feature_movie_details.MovieDetailsScreen
import com.dpfht.demotmdbcompose.feature_movie_reviews.MovieReviewsScreen
import com.dpfht.demotmdbcompose.feature_movie_trailer.MovieTrailerActivity
import com.dpfht.demotmdbcompose.feature_movies_by_genre.MoviesByGenreScreen
import com.dpfht.demotmdbcompose.feature_splash.SplashScreen
import com.dpfht.demotmdbcompose.framework.navigation.NavigationService
import com.dpfht.demotmdbcompose.framework.navigation.Screen
import com.dpfht.demotmdbcompose.framework.navigation.Screen.IntroBaseRoute
import com.dpfht.demotmdbcompose.ui.theme.DemoTmdbComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

lateinit var appWindowTitle: String

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @Inject
  lateinit var navigationService: NavigationService

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    appWindowTitle = "${getString(R.string.app_name)}${getString(R.string.running_mode)}"

    setContent {
      val navController = rememberNavController()
      AppNavigation(navigationService = navigationService, navController = navController)

      DemoTmdbComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          NavHost(
            navController = navController,
            startDestination = IntroBaseRoute.route
          ) {
            introGraph()
            mainGraph()
          }
        }
      }
    }
  }
}

@Composable
fun AppNavigation(navigationService: NavigationService, navController: NavHostController) {
  var showSheet by remember { mutableStateOf(false) }
  var errorMessage by remember { mutableStateOf("") }

  if (showSheet) {
    AppBottomSheet(
      msg = errorMessage
    ) {
      navigationService.navigate(Screen.None)
      showSheet = false
    }
  }

  navigationService.screen.collectAsState().value.also { screen ->
    when (screen) {
      Screen.NavigateUp -> {
        navController.navigateUp()
        navigationService.navigate(Screen.None)
      }
      Screen.Genre -> {
        navController.navigate(Screen.MainBaseRoute.route) {
          popUpTo(IntroBaseRoute.route)
        }
      }
      is Screen.Error -> {
        errorMessage = screen.message
        showSheet = true
        navigationService.navigate(Screen.None)
      }
      is Screen.MoviesByGenre -> {
        navController.navigate(screen.createRoute())
        navigationService.navigate(Screen.None)
      }
      is Screen.MovieDetails -> {
        navController.navigate(screen.createRoute())
        navigationService.navigate(Screen.None)
      }
      is Screen.MovieReviews -> {
        navController.navigate(screen.createRoute())
        navigationService.navigate(Screen.None)
      }
      is Screen.MovieTrailer -> {
        val movieId = screen.movieId
        val itn = Intent(LocalContext.current, MovieTrailerActivity::class.java)
        itn.putExtra("movie_id", movieId)
        LocalContext.current.startActivity(itn)

        navigationService.navigate(Screen.None)
      }
      else -> {
        if (screen.route.isNotEmpty()) {
          navController.navigate(screen.route)
        }
      }
    }
  }
}

fun NavGraphBuilder.introGraph() {
  navigation(route = IntroBaseRoute.route, startDestination = Screen.Splash.route) {
    composable(route = Screen.Splash.route) {
      SplashScreen()
    }
  }
}

fun NavGraphBuilder.mainGraph() {
  navigation(route = Screen.MainBaseRoute.route, startDestination = Screen.Genre.route) {
    composable(route = Screen.Genre.route) {
      GenreScreen(screenTitle = appWindowTitle)
    }
    composable(route = Screen.MoviesByGenre.route) { backStackEntry ->
      val genreId = backStackEntry.arguments?.getString("genreId")
      requireNotNull(genreId) { stringResource(id = R.string.app_text_genre_id_required) }

      val genreName = backStackEntry.arguments?.getString("genreName")
      requireNotNull(genreName) { stringResource(id = R.string.app_text_genre_name_required) }

      val theGenreId = try {
        genreId.toInt()
      } catch (e: Exception) {
        e.printStackTrace()
        -1
      }

      MoviesByGenreScreen(screenTitle = appWindowTitle, genreId = theGenreId, genreName = genreName)
    }
    composable(route = Screen.MovieDetails.route) { backStackEntry -> 
      val movieId = backStackEntry.arguments?.getString("movieId")
      requireNotNull(movieId) { stringResource(id = R.string.app_text_movie_id_required) }
      
      val theMovieId = try {
        movieId.toInt()
      } catch (e: Exception) {
        e.printStackTrace()
        -1
      }
      
     MovieDetailsScreen(screenTitle = appWindowTitle, movieId = theMovieId)
    }
    composable(route = Screen.MovieReviews.route) { backStackEntry ->
      val movieId = backStackEntry.arguments?.getString("movieId")
      requireNotNull(movieId) { stringResource(id = R.string.app_text_movie_id_required) }

      val movieTitle = backStackEntry.arguments?.getString("movieTitle")
      requireNotNull(movieTitle) { stringResource(id = R.string.app_text_movie_title_required) }

      val theMovieId = try {
        movieId.toInt()
      } catch (e: Exception) {
        e.printStackTrace()
        -1
      }

      MovieReviewsScreen(screenTitle = appWindowTitle, movieId = theMovieId, movieTitle = movieTitle)
    }
  }
}
