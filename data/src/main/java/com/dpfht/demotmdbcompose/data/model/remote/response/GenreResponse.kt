package com.dpfht.demotmdbcompose.data.model.remote.response

import androidx.annotation.Keep
import com.dpfht.demotmdbcompose.data.model.remote.Genre
import com.dpfht.demotmdbcompose.domain.entity.GenreDomain
import com.dpfht.demotmdbcompose.domain.entity.GenreEntity

@Keep
data class GenreResponse(
    val genres: List<Genre>? = listOf()
)

fun GenreResponse.toDomain(): GenreDomain {
    val genreEntities = genres?.map { GenreEntity(it.id ?: -1, it.name ?: "") }

    return GenreDomain(genreEntities?.toList() ?: listOf())
}
