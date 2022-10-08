package com.joshhn.flixsterpart2

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


const val TOP_RATED_MOVIE_EXTRA = "TOP_RATED_MOVIE_EXTRA"
private const val TAG = "TopRatedMoviesAdapter"

class TopRatedMovieAdapter (private val context: Context, private val topRatedMovies: List<TopRatedMovie>) :
    RecyclerView.Adapter<TopRatedMovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_top_rated_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO: Get the individual article and bind to holder
        val movie = topRatedMovies[position]
        holder.bind(movie)
    }

    override fun getItemCount() =  topRatedMovies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private var poster: ImageView = itemView.findViewById(R.id.iv_poster_movie_top)
        var title: TextView = itemView.findViewById(R.id.tv_title_movie_top)

        init {
            itemView.setOnClickListener(this)
        }

        // TODO: Write a helper method to help set up the onBindViewHolder method

        override fun onClick(v: View?) {
            // TODO: Get selected article
            val movie = topRatedMovies[absoluteAdapterPosition]
            // TODO: Navigate to Details screen and pass selected article
            val intent = Intent(context, TopRatedMovieDetailsActivity::class.java)
            intent.putExtra(TOP_RATED_MOVIE_EXTRA, movie)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, (poster as View?)!!, "poster")
            context.startActivity(intent, options.toBundle())
        }

        fun bind(movie: TopRatedMovie) {
            title.text = movie.title
            val radius = 40; // corner radius, higher value = more rounded
            val margin = 10; // crop margin, set to 0 for corners with no crop
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w500${movie.poster}")
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_image_not_supported_24)
                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(radius,margin)))
                .into(poster)

        }
    }
}
