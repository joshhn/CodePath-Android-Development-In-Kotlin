package com.joshhn.flixsterpart2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class UpcomingMovieDetailsActivity : AppCompatActivity() {
    private lateinit var ivPoster: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvReleaseDate: TextView
    private lateinit var tvOverview: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_movie_details)

        ivPoster = findViewById(R.id.iv_poster_movie)
        tvTitle = findViewById(R.id.tv_title_movie)
        tvReleaseDate = findViewById(R.id.tv_release_date)
        tvOverview = findViewById(R.id.tv_overview)

        // TODO: Get the extra from the Intent
        val movie = intent.getSerializableExtra(UPCOMING_MOVIE_EXTRA) as UpcomingMovie

        // TODO: Set the title, byline, and abstract information from the article
        tvTitle.text = movie.title
        tvOverview.text = movie.overview
        tvReleaseDate.text = movie.releaseDate
        // TODO: Load the media image
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movie.poster}")
            .placeholder(R.drawable.ic_baseline_image_24)
            .error(R.drawable.ic_baseline_image_not_supported_24)
            .into(ivPoster)
    }
}