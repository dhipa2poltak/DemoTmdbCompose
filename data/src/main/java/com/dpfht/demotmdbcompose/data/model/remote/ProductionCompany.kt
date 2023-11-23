package com.dpfht.demotmdbcompose.data.model.remote

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
@Suppress("unused")
data class ProductionCompany(
    @SerializedName("name")
    @Expose
    val name: String? = "",

    @SerializedName("origin_country")
    @Expose
    val originCountry: String? = "",

    @SerializedName("logo_path")
    @Expose
    val logoPath: String? = "",

    @SerializedName("id")
    @Expose
    val id: Int? = -1,
)
