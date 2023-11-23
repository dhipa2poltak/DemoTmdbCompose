package com.dpfht.demotmdbcompose.feature_splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SplashScreen(
  viewModel: SplashViewModel = hiltViewModel()
) {

  LaunchedEffect(Unit) {
    viewModel.start()
  }

  Box(
    modifier = Modifier.fillMaxSize()
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.SpaceAround,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = stringResource(id = R.string.splash_text_app_name),
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold
      )
      Image(
        painter = painterResource(id = R.drawable.splash),
        modifier = Modifier
          .width(200.dp)
          .height(200.dp),
        contentDescription = null
      )
      CircularProgressIndicator()
      Text(
        text = stringResource(id = R.string.splash_text_loading),
        fontSize = 18.sp
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
  SplashScreen()
}
