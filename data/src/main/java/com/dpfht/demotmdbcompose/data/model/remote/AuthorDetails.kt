package com.dpfht.demotmdbcompose.data.model.remote

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
@Suppress("unused")
data class AuthorDetails(
    val name: String? = "",
    val username: String? = "",

    @SerializedName("avatar_path")
    @Expose
    val avatarPath: String? = "",

    val rating: Float? = 0.0f
)
