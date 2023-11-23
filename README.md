# DemoTmdbCompose

**Screenshots:**
| <img src="docs/screenshots/genres.jpg">          | <img src="docs/screenshots/movies_by_genre.jpg"> |
| ------------------------------------------------ | ------------------------------------------------ |
| <img src="docs/screenshots/movie_details.jpg">   | <img src="docs/screenshots/movie_reviews.jpg">   |

**Technology Stack:**
- Kotlin Programming Language
- Clean Architecture
- MVVM Architecture Pattern
- Hilt Dependency Injection
- Jetpack Compose
- View Binding
- ViewModel
- LiveData
- Coroutine
- Flow
- Retrofit REST + OkHttp
- GSON Serialization
- Coil
- Android YouTube Player (pierfrancescosoffritti)
- Gradle build flavors
- BuildSrc + Kotlin DSL
- Proguard
- Git

**To run the project in DEBUG MODE:**

Get the api key from [api.themoviedb.org](https://api.themoviedb.org/).

Create file namely “key.properties” in the root project with following contents:

storePassword=<your_keystore_password> <br />
keyPassword=<your_key_password> <br />
keyAlias=<your_key_alias> <br />
storeFile=<path_to_keystore_file> <br />
apiKey=<your_api_key> <br />

once you have created it, open the project with Android Studio, build the project and run the project.
