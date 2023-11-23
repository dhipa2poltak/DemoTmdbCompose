package com.dpfht.demotmdbcompose.data.model.remote

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
@Suppress("unused")
data class Movie(
    val adult: Boolean? = false,

    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String? = "",

    @SerializedName("genre_ids")
    @Expose
    val genreIds: List<Int>? = listOf(),

    val id: Int? = -1,

    @SerializedName("original_language")
    @Expose
    val originalLanguage: String? = "",

    @SerializedName("original_title")
    @Expose
    val originalTitle: String? = "",

    val overview: String? = "",
    val popularity: Float? = 0.0f,

    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = "",

    @SerializedName("release_date")
    @Expose
    val releaseDate: String? = "",

    val title: String? = "",
    val video: Boolean? = false,

    @SerializedName("vote_average")
    @Expose
    val voteAverage: Float? = 0.0f,

    @SerializedName("vote_count")
    @Expose
    val voteCount: Int? = -1
)
