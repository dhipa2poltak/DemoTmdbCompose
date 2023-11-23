import java.io.FileInputStream
import java.util.Properties

val keystoreProperties = Properties()
val keystorePropertiesFile = rootProject.file("key.properties")
if (keystorePropertiesFile.exists()) {
  keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("kotlin-kapt")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.dpfht.demotmdbcompose.framework"
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

      buildConfigField("String", "API_KEY", "\"${keystoreProperties["apiKey"] as String}\"")
    }
    debug {
      buildConfigField("String", "API_KEY", "\"${keystoreProperties["apiKey"] as String}\"")
    }
  }
  buildFeatures {
    buildConfig = true
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
  implementation(project(":data"))

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

  implementation(Deps.okHttp)
  implementation(Deps.gsonConverter)
  implementation(Deps.loggingInterceptor)
  implementation(Deps.gson)
}
