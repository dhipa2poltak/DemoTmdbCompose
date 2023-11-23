package com.dpfht.demotmdbcompose.data.model.remote

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
@Suppress("unused")
data class SpokenLanguage(
    @SerializedName("iso_639_1")
    @Expose
    val iso6391: String? = "",

    @SerializedName("name")
    @Expose
    val name: String? = "",

    @SerializedName("english_name")
    @Expose
    val englishName: String? = ""
)
