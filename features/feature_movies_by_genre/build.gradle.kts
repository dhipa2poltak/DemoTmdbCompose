plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("kotlin-kapt")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.dpfht.demotmdbcompose.feature_movies_by_genre"
  compileSdk = ConfigData.compileSdkVersion

  defaultConfig {
    minSdk = ConfigData.minSdkVersion

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.4.3"
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
}

dependencies {

  implementation(project(":domain"))
  implementation(project(":framework"))

  implementation(Deps.coreKtx)
  implementation(Deps.appCompat)
  implementation(Deps.material)
  testImplementation(Deps.jUnit)
  androidTestImplementation(Deps.jUnitExt)
  androidTestImplementation(Deps.espresso)

  implementation(Deps.activityCompose)
  implementation(platform(Deps.composeBom))
  implementation(Deps.composeUi)
  implementation(Deps.composeUiGraphics)
  implementation(Deps.composeUiToolingPreview)

  implementation(Deps.composeMaterial3)
  implementation(Deps.constraintLayoutCompose)

  implementation(Deps.hilt)
  kapt(Deps.hiltCompiler)
  implementation(Deps.hiltNavigationCompose)

  implementation(Deps.pagingRuntimeKtx)
  testImplementation(Deps.pagingCommonKtx)
  implementation(Deps.pagingCompose)

  implementation(Deps.coilCompose)
}
