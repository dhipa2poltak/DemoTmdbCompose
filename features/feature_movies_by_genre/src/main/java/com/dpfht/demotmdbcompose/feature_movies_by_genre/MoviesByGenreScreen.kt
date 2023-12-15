package com.dpfht.demotmdbcompose.feature_movies_by_genre

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImagePainter.State.Success
import coil.compose.rememberAsyncImagePainter
import com.dpfht.demotmdbcompose.domain.entity.MovieEntity
import com.dpfht.demotmdbcompose.feature_movies_by_genre.event_state.UIEvent
import com.dpfht.demotmdbcompose.framework.commons.ui.components.SharedTopAppBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviesByGenreScreen(
  modifier: Modifier = Modifier,
  screenTitle: String,
  genreId: Int,
  genreName: String,
  viewModel: MoviesByGenreViewModel = hiltViewModel()
) {
  val state = viewModel.uiState.value
  val moviePagingItems: LazyPagingItems<MovieEntity> = viewModel.uiState.value.moviesState.collectAsLazyPagingItems()

  val pullRefreshState = rememberPullRefreshState(
    refreshing = state.isLoading,
    onRefresh = {
      viewModel.onEvent(UIEvent.Refresh)
    }
  )

  if (state.errorMessage.isNotEmpty()) {
    viewModel.showErrorMessage(state.errorMessage)
  }

  LaunchedEffect(Unit) {
    viewModel.onEvent(UIEvent.Init(genreId = genreId))
  }

  Scaffold(
    topBar = {
      SharedTopAppBar(
        title = screenTitle,
        onNavigateBack = {
          viewModel.onEvent(UIEvent.OnBackPressed)
        }
      )
    },
    content = { padding ->
      Box(
        modifier = Modifier
          .padding(padding)
          .fillMaxSize()
      ) {
        ConstraintLayout(
          modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxSize()
        ) {

          val (tvTitle, divider, movieBox) = createRefs()

          val colors = MaterialTheme.colorScheme

          Text(
            String.format(stringResource(id = R.string.movies_by_genre_text_genre_movies), genreName),
            color = Color.Black,
            modifier = modifier
              .padding(vertical = 8.dp)
              .constrainAs(tvTitle) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(divider.top)
              },
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
          )

          HorizontalDivider(
            modifier = modifier
              .height(1.dp)
              .constrainAs(divider) {
                start.linkTo(parent.start)
                top.linkTo(tvTitle.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(movieBox.top)
              },
            color = colors.onSurface.copy(alpha = .2f)
          )

          Box(
            modifier = Modifier
              .fillMaxSize()
              .constrainAs(movieBox) {
                start.linkTo(parent.start)
                top.linkTo(divider.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
              }
              .pullRefresh(pullRefreshState)
          ) {
            if (moviePagingItems.itemCount > 0) {
              LazyColumn(
                modifier = modifier
                  .fillMaxSize()
              ) {
                item { Spacer(modifier = Modifier.height(8.dp)) }
                items(moviePagingItems.itemCount) { index ->
                  ItemMovie(
                    movie = moviePagingItems[index] ?: MovieEntity(),
                    onClickMovieItem = { movieId ->
                      viewModel.onEvent(UIEvent.OnClickMovieItem(movieId))
                    }
                  )
                }
                item { Spacer(modifier = Modifier.height(32.dp)) }
              }
            }

            PullRefreshIndicator(
              refreshing = state.isLoading,
              state = pullRefreshState,
              modifier = Modifier.align(Alignment.TopCenter)
            )
          }
        }

        /*
        if (state.isLoading) {
          CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        */
      }
    }
  )
}

@Composable
fun ItemMovie(
  movie: MovieEntity,
  onClickMovieItem: (movieId: Int) -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .clip(RoundedCornerShape(8.dp))
      .clickable { onClickMovieItem(movie.id) },
    shape = RoundedCornerShape(8.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(
      defaultElevation = 8.dp
    )
  ) {
    val painter = rememberAsyncImagePainter(movie.imageUrl)
    val transition by animateFloatAsState(
      targetValue = if (painter.state is Success) 1f else 0f, label = ""
    )

    Row(
      modifier = Modifier.padding(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
          .width(120.dp)
          .height(120.dp)
          .clip(RoundedCornerShape(8.dp))
          .alpha(transition)
      )

      Column(
        modifier = Modifier.padding(start = 4.dp, end = 8.dp),
        verticalArrangement = Arrangement.Center
      ) {
        Text(
          text = movie.title,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = movie.overview,
          maxLines = 4,
          overflow = TextOverflow.Ellipsis
        )
      }
    }
  }
}
