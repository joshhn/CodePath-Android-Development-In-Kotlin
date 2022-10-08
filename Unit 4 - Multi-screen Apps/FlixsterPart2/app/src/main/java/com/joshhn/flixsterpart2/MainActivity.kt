package com.joshhn.flixsterpart2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.joshhn.flixsterpart2.databinding.ActivityMainBinding
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"
private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"


class MainActivity : AppCompatActivity() {
    private lateinit var rvUpcomingMovie: RecyclerView
    private lateinit var rvTopRatedMovie: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private val upcomingMovies = mutableListOf<UpcomingMovie>()
    private val topRatedMovies = mutableListOf<TopRatedMovie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        rvUpcomingMovie = findViewById(R.id.rv_upcoming_movie)
        rvTopRatedMovie = findViewById(R.id.rv_top_rated_movies)
        // TODO: Set up ArticleAdapter with articles
        val upcomingMovieAdapter = UpcomingMovieAdapter(this, upcomingMovies)
        rvUpcomingMovie.adapter = upcomingMovieAdapter

        rvUpcomingMovie.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        val topRatedMovieAdapter = TopRatedMovieAdapter(this, topRatedMovies)
        rvTopRatedMovie.adapter = topRatedMovieAdapter

        rvTopRatedMovie.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY

        client.get("https://api.themoviedb.org/3/movie/upcoming",params, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch upcoming movies: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched upcoming movies: $json")
                try {
                    val parsedJson = createJson().decodeFromString(
                        SearchNewsResponse.serializer(),
                        json.jsonObject.toString()
                    )
                    // TODO: Do something with the returned json (contains article information)
                    parsedJson.result?.let { list ->
                        upcomingMovies.addAll(list)
                    }
                    // Reload the screen
                    upcomingMovieAdapter.notifyDataSetChanged()

                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }
        })

        client.get("https://api.themoviedb.org/3/movie/top_rated",params, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch top rated movies: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched top rated movies: $json")
                try {
                    val parsedJson = createJson().decodeFromString(
                        SearchNewsResponse2.serializer(),
                        json.jsonObject.toString()
                    )
                    // TODO: Do something with the returned json (contains article information)
                    parsedJson.result?.let { list ->
                        topRatedMovies.addAll(list)
                    }
                    // Reload the screen
                    topRatedMovieAdapter.notifyDataSetChanged()

                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }
        })
    }
}