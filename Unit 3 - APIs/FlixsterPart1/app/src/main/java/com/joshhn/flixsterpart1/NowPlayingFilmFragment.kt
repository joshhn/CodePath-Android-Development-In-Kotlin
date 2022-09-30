package com.joshhn.flixsterpart1


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers


private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class NowPlayingFilmFragment() : Fragment(), OnListFragmentInteractionListener {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_now_playing_films_list, container, false)
            val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
            val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
            val context = view.context
            recyclerView.layoutManager = GridLayoutManager(context, 1)
            updateAdapter(progressBar, recyclerView)
            return view
        }

        private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
            progressBar.show()

            // Create and set up an AsyncHTTPClient() here
            val client = AsyncHttpClient()
            val params = RequestParams()
            params["api_key"] = API_KEY

            // Using the client, perform the HTTP request
            client[
                    "https://api.themoviedb.org/3/movie/now_playing",
                    params,
                    object : JsonHttpResponseHandler()
                    {
                        /*
                         * The onSuccess function gets called when
                         * HTTP response status is "200 OK"
                         */
                        override fun onSuccess(
                            statusCode: Int,
                            headers: Headers,
                            json: JsonHttpResponseHandler.JSON
                        ) {
                            // The wait for a response is over
                            progressBar.hide()

                            //TODO - Parse JSON into Models
                            val resultsJSON = json.jsonObject.get("results")

                            val gson = Gson()
                            val arrayFilmType = object: TypeToken<List<NowPlayingFilm>>() {}.type
                            val models : List<NowPlayingFilm> = gson.fromJson(resultsJSON.toString(), arrayFilmType)
                            val sortedFilmList = models.sortedBy { nowPlayingFilm -> nowPlayingFilm.voteAvg?.times(-1) }

                            recyclerView.adapter = NowPlayingFilmsRecyclerViewAdapter(sortedFilmList, this@NowPlayingFilmFragment)

                            // Look for this in Logcat:
                            Log.d("NowPlayingFilmFragment", "response successful")
                        }

                        /*
                         * The onFailure function gets called when
                         * HTTP response status is "4XX" (eg. 401, 403, 404)
                         */
                        override fun onFailure(
                            statusCode: Int,
                            headers: Headers?,
                            errorResponse: String,
                            t: Throwable?
                        ) {
                            // The wait for a response is over
                            progressBar.hide()

                            // If the error is not null, log it!
                            t?.message?.let {
                                Log.e("NowPlayingFilmFragment", errorResponse)
                            }
                        }
                    }]

        }

        /*
         * What happens when a particular book is clicked.
         */
        override fun onItemClick(item: NowPlayingFilm) {
            Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
        }
}
