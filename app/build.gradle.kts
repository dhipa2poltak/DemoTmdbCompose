import java.io.FileInputStream
import java.util.Properties

val keystoreProperties = Properties()
val keystorePropertiesFile = rootProject.file("key.properties")
if (keystorePropertiesFile.exists()) {
  keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("kotlin-kapt")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.dpfht.demotmdbcompose"
  compileSdk = ConfigData.compileSdkVersion

  signingConfigs {
    create("release") {
      storeFile = file(keystoreProperties["storeFile"] as String)
      storePassword = keystoreProperties["storePassword"] as String
      keyAlias = keystoreProperties["keyAlias"] as String
      keyPassword = keystoreProperties["keyPassword"] as String
    }
  }

  defaultConfig {
    minSdk = ConfigData.minSdkVersion
    targetSdk = ConfigData.targetSdkVersion

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

      signingConfig = signingConfigs["release"]

      manifestPlaceholders["appNameSuffix"] = ""
      resValue("string", "running_mode", "")
    }
    debug {
      isMinifyEnabled = false
      isShrinkResources = false

      applicationIdSuffix = ".debug"
      versionNameSuffix = "-debug"

      manifestPlaceholders["appNameSuffix"] = "-(debug)"
      resValue("string", "running_mode", "-(debug)")
    }
  }

  flavorDimensions.add("default")

  productFlavors {
    create("prod") {
      applicationId = "com.dpfht.demotmdbcompose"
      versionCode = ConfigData.versionCode
      versionName = ConfigData.versionName

      manifestPlaceholders["appName"] = "Demo Tmdb Compose"
      resValue("string", "app_name", "Demo Tmdb Compose")
    }
    create("dev") {
      applicationId = "com.dpfht.demotmdbcompose.dev"
      versionCode = ConfigData.versionCodeDev
      versionName = ConfigData.versionNameDev

      manifestPlaceholders["appName"] = "Demo Tmdb Compose (DEV)"
      resValue("string", "app_name", "Demo Tmdb Compose (DEV)")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
  buildFeatures {
    buildConfig = true
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.4.3"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {

  implementation(project(":domain"))
  implementation(project(":framework"))
  implementation(project(":features:feature_error_message"))
  implementation(project(":features:feature_splash"))
  implementation(project(":features:feature_genre"))
  implementation(project(":features:feature_movies_by_genre"))
  implementation(project(":features:feature_movie_details"))
  implementation(project(":features:feature_movie_reviews"))
  implementation(project(":features:feature_movie_trailer"))

  implementation(Deps.coreKtx)
  implementation(Deps.lifecycleRuntimeKtx)
  implementation(Deps.activityCompose)
  implementation(platform(Deps.composeBom))
  implementation(Deps.composeUi)
  implementation(Deps.composeUiGraphics)
  implementation(Deps.composeUiToolingPreview)
  implementation(Deps.composeMaterial3)
  testImplementation(Deps.jUnit)
  androidTestImplementation(Deps.jUnitExt)
  androidTestImplementation(Deps.espresso)
  androidTestImplementation(platform(Deps.composeBom))
  androidTestImplementation(Deps.composeUiTestJUnit4)
  debugImplementation(Deps.composeUiTooling)
  debugImplementation(Deps.composeUiTestManifest)

  implementation(Deps.navigationCompose)

  implementation(Deps.hilt)
  kapt(Deps.hiltCompiler)
}
