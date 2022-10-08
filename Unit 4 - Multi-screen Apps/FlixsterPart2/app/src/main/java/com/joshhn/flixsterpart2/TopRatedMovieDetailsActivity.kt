package com.joshhn.flixsterpart2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.joshhn.flixsterpart2.databinding.ActivityTopRatedMovieDetailsBinding

class TopRatedMovieDetailsActivity : AppCompatActivity() {

    private var binding: ActivityTopRatedMovieDetailsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopRatedMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // TODO: Get the extra from the Intent
        val movie = intent.getSerializableExtra(TOP_RATED_MOVIE_EXTRA) as TopRatedMovie

        // TODO: Set the title, byline, and abstract information from the article
        binding?.tvTitleMovie?.text = movie.title
        binding?.tvOverview?.text = movie.overview
        binding?.tvReleaseDate?.text = movie.releaseDate
        // TODO: Load the media image
        binding?.ivPosterMovie?.let {
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500${movie.poster}")
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_image_not_supported_24)
                .into(it)
        }
    }
}