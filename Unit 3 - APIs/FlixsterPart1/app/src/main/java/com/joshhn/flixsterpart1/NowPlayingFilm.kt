package com.joshhn.flixsterpart1

import com.google.gson.annotations.SerializedName

class NowPlayingFilm {
    @JvmField
    @SerializedName("title")
    var title: String? = null

    @SerializedName("poster_path")
    var poster: String? = null

    @SerializedName("overview")
    var description: String? = null

    @SerializedName("vote_average")
    var voteAvg: Double? = null

}