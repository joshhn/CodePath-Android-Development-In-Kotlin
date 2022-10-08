package com.joshhn.flixsterpart2

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SearchNewsResponse(
    @SerialName("results")
    val result: List<UpcomingMovie>?
)

@Keep
@Serializable
data class UpcomingMovie(
    @SerialName("poster_path")
    val poster: String?,
    @SerialName("overview")
    val overview: String?,
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("vote_average")
    var voteAvg: Double? = null,
    @SerialName("adult")
    var adult: Boolean? = null
) : java.io.Serializable


