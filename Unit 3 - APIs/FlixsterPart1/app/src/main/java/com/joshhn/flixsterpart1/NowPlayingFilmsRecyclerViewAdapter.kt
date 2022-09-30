package com.joshhn.flixsterpart1

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joshhn.flixsterpart1.R.id


class NowPlayingFilmsRecyclerViewAdapter(
    private val films: List<NowPlayingFilm>,
    private val mListener: OnListFragmentInteractionListener?
)
    : RecyclerView.Adapter<NowPlayingFilmsRecyclerViewAdapter.FilmViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_now_playing_film, parent, false)

        return FilmViewHolder(view)
    }

    /**
     * This inner class lets us refer to all the different View elements
     * (Yes, the same ones as in the XML layout files!)
     */
    inner class FilmViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: NowPlayingFilm? = null
        val mFilmTitle: TextView = mView.findViewById<View>(id.film_title) as TextView
        val mFilmDescription: TextView = mView.findViewById<View>(id.film_description) as TextView
        val mFilmPoster: ImageView = mView.findViewById<View>(id.film_poster) as ImageView
    }

    /**
     * This lets us "bind" each Views in the ViewHolder to its' actual data!
     */
    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = films[position]

        holder.mItem = film
        holder.mFilmTitle.text = film.title
        holder.mFilmDescription.text = film.description

        holder.mView.setOnClickListener {
            holder.mItem?.let { book ->
                mListener?.onItemClick(book)
            }
        }

        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500${film.poster}")
            .placeholder(R.drawable.ic_baseline_image_24)
            .error(R.drawable.ic_baseline_image_not_supported_24)
            .into(holder.mFilmPoster)
    }

    /**
     * Remember: RecyclerView adapters require a getItemCount() method.
     */
    override fun getItemCount(): Int {
        return films.size
    }

}