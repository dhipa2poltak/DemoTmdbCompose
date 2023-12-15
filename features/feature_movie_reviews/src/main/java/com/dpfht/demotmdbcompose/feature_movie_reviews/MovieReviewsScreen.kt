package com.dpfht.demotmdbcompose.feature_movie_reviews

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImagePainter.State.Success
import coil.compose.rememberAsyncImagePainter
import com.dpfht.demotmdbcompose.domain.entity.ReviewEntity
import com.dpfht.demotmdbcompose.feature_movie_reviews.event_state.UIEvent
import com.dpfht.demotmdbcompose.feature_movie_reviews.event_state.UIEvent.OnBackPressed
import com.dpfht.demotmdbcompose.framework.commons.ui.components.SharedTopAppBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieReviewsScreen(
  modifier: Modifier = Modifier,
  screenTitle: String,
  movieId: Int,
  movieTitle: String,
  viewModel: MovieReviewsViewModel = hiltViewModel()
) {
  val state = viewModel.uiState.value
  val reviewPagingItems: LazyPagingItems<ReviewEntity> = viewModel.uiState.value.reviewState.collectAsLazyPagingItems()

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
    viewModel.onEvent(UIEvent.Init(movieId = movieId))
  }

  Scaffold(
    topBar = {
      SharedTopAppBar(
        title = screenTitle,
        onNavigateBack = {
          viewModel.onEvent(OnBackPressed)
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
            .padding(top = 40.dp)
            .fillMaxSize()
        ) {

          val (tvTitle, tvMovieTitle, divider, reviewBox) = createRefs()

          val colors = MaterialTheme.colorScheme

          Text(
            stringResource(id = R.string.movie_reviews_text_review),
            color = Color.Black,
            modifier = modifier
              .constrainAs(tvTitle) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(tvMovieTitle.top)
              },
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
          )

          Text(
            movieTitle,
            color = Color.Black,
            modifier = modifier
              .padding(bottom = 8.dp)
              .constrainAs(tvMovieTitle) {
                start.linkTo(parent.start)
                top.linkTo(tvTitle.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(divider.top)
              },
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
          )

          HorizontalDivider(
            modifier = modifier
              .height(1.dp)
              .constrainAs(divider) {
                start.linkTo(parent.start)
                top.linkTo(tvMovieTitle.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(reviewBox.top)
              },
            color = colors.onSurface.copy(alpha = .2f)
          )

          Box(
            modifier = Modifier
              .fillMaxSize()
              .constrainAs(reviewBox) {
                start.linkTo(parent.start)
                top.linkTo(divider.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
              }
              .pullRefresh(pullRefreshState)
          ) {
            LazyColumn(
              modifier = modifier
                .fillMaxSize()
            ) {
              item { Spacer(modifier = Modifier.height(8.dp)) }
              items(reviewPagingItems.itemCount) { index ->
                ItemReview(
                  review = reviewPagingItems[index] ?: ReviewEntity()
                )
              }
              item { Spacer(modifier = Modifier.height(32.dp)) }
            }

            PullRefreshIndicator(
              refreshing = state.isLoading,
              state = pullRefreshState,
              modifier = Modifier.align(Alignment.TopCenter)
            )
          }
        }
      }
    }
  )
}

@Composable
fun ItemReview(
  review: ReviewEntity
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .clip(RoundedCornerShape(8.dp))
      .clickable {},
    shape = RoundedCornerShape(8.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(
      defaultElevation = 8.dp
    )
  ) {
    val painter = rememberAsyncImagePainter(review.authorDetails?.avatarPath)
    val transition by animateFloatAsState(
      targetValue = if (painter.state is Success) 1f else 0f, label = ""
    )

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
      verticalArrangement = Arrangement.Center
    ) {
      Text(
        text = review.content,
        modifier = Modifier.fillMaxWidth()
      )
      Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .width(50.dp)
          .height(50.dp)
          .clip(RoundedCornerShape(8.dp))
          .alpha(transition)
          .align(Alignment.End)
      )
      Text(
        text = review.author,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
          .align(Alignment.End)
      )
    }
  }
}
