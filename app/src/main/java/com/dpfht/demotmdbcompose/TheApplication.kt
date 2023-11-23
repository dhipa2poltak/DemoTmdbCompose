package com.dpfht.demotmdbcompose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TheApplication: Application() {

  companion object {
    lateinit var INSTANCE: TheApplication
  }

  override fun onCreate() {
    INSTANCE = this
    super.onCreate()
  }
}
