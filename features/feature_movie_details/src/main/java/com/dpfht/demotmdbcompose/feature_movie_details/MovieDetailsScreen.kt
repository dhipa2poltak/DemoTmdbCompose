package com.dpfht.demotmdbcompose.feature_movie_details

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter.State.Success
import coil.compose.rememberAsyncImagePainter
import com.dpfht.demotmdbcompose.feature_movie_details.event_state.UIEvent
import com.dpfht.demotmdbcompose.feature_movie_details.event_state.UIEvent.OnBackPressed
import com.dpfht.demotmdbcompose.framework.commons.ui.components.SharedTopAppBar

@Composable
fun MovieDetailsScreen(
  screenTitle: String,
  movieId: Int,
  viewModel: MovieDetailsViewModel = hiltViewModel()
) {

  val state = viewModel.uiState.value

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
        Column(
          verticalArrangement = Arrangement.Top,
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
        ) {
          val painter = rememberAsyncImagePainter(state.imageUrl)
          val transition by animateFloatAsState(
            targetValue = if (painter.state is Success) 1f else 0f, label = ""
          )

          Text(
            text = state.title,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
              .padding(vertical = 10.dp)
          )
          Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
              .width(320.dp)
              .height(320.dp)
              .padding(vertical = 10.dp)
              .clip(RoundedCornerShape(8.dp))
              .alpha(transition)
          )
          Text(
            text = state.description,
            modifier = Modifier.padding(top = 10.dp, start = 15.dp, end = 15.dp)
          )
          Spacer(
            modifier = Modifier.height(20.dp)
          )
          if (state.errorMessage.isEmpty()) {
            TextButton(
              onClick = {
                viewModel.onEvent(UIEvent.OnClickShowReview(movieId))
              }
            ) {
              Text(
                text = stringResource(id = R.string.movie_details_text_show_review),
                color = Color.Blue
              )
            }
            TextButton(
              onClick = {
                viewModel.onEvent(UIEvent.OnClickShowTrailer(movieId))
              }
            ) {
              Text(
                text = stringResource(id = R.string.movie_details_text_show_trailer),
                color = Color.Blue
              )
            }
          }
        }

        if (state.isLoading) {
          CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
      }
    }
  )
}
