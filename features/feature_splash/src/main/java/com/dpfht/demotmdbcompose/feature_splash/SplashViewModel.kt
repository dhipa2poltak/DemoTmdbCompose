package com.dpfht.demotmdbcompose.feature_splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.demotmdbcompose.framework.Constants
import com.dpfht.demotmdbcompose.framework.navigation.NavigationService
import com.dpfht.demotmdbcompose.framework.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val navigationService: NavigationService
): ViewModel() {

  fun start() {
    viewModelScope.launch {
      delay(Constants.DELAY_SPLASH)
      navigationService.navigate(Screen.Genre)
    }
  }
}
