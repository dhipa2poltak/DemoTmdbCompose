pluginManagement {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "DemoTmdbCompose"
include(":app")
include(":domain")
include(":data")
include(":framework")
include(":features:feature_splash")
include(":features:feature_genre")
include(":features:feature_movies_by_genre")
include(":features:feature_movie_details")
include(":features:feature_movie_reviews")
include(":features:feature_movie_trailer")
include(":features:feature_error_message")
