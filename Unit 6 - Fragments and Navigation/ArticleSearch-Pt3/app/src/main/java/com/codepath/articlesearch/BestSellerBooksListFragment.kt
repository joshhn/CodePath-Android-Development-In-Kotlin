package com.codepath.articlesearch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONException
import org.json.JSONObject

private const val TAG = "BooksListFragment"
private const val SEARCH_API_KEY = BuildConfig.API_KEY
private const val BOOKS_SEARCH_URL =
    "\"https://api.nytimes.com/svc/books/v3/lists/current/hardcover-fiction.json"

class BestSellerBooksListFragment : Fragment() {

    private var books = mutableListOf<Book>()
    private lateinit var booksRecyclerView: RecyclerView
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Change this statement to store the view in a variable instead of a return statement
        val view = inflater.inflate(R.layout.fragment_best_seller_books_list, container, false)

        // Add these configurations for the recyclerView and to configure the adapter
        val layoutManager = LinearLayoutManager(context)
        booksRecyclerView = view.findViewById(R.id.article_recycler_view)
        booksRecyclerView.layoutManager = layoutManager
        booksRecyclerView.setHasFixedSize(true)
        bookAdapter = BookAdapter(view.context,books)
        booksRecyclerView.adapter = bookAdapter

        // Update the return statement to return the inflated view from above
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Call the new method within onViewCreated
        fetchArticles()
    }

    companion object {
        fun newInstance(): ArticleListFragment {
            return ArticleListFragment()
        }
    }

    private fun fetchArticles() {
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api-key"] = SEARCH_API_KEY
        client.get(BOOKS_SEARCH_URL,params, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch books: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched books: $json")
                try {

                    val resultsJSON : JSONObject = json.jsonObject.get("results") as JSONObject
                    val booksRawJSON : String = resultsJSON.get("books").toString()

                    Log.i("json", resultsJSON.toString())

                    val gson = Gson()

                    val arrayBookType = object: TypeToken<List<Book>>() {}.type

                    books = gson.fromJson(booksRawJSON,arrayBookType)

                    // Look for this in Logcat:
                    Log.d(TAG, "response successful")
                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }
        })
    }
}

