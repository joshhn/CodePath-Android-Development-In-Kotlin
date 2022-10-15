package com.joshhn.bitfit

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joshhn.bitfit.databinding.ActivityMainBinding
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.Headers
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var imageUrl: String
    private var foodList: MutableList<Food> = mutableListOf()
    private var totalCaloriesAte: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

//        val client = AsyncHttpClient()
//        val params = RequestParams()
//
//        client.get("https://api.thecatapi.com/v1/images/search",params, object : JsonHttpResponseHandler() {
//            override fun onFailure(
//                statusCode: Int,
//                headers: Headers?,
//                response: String?,
//                throwable: Throwable?
//            ) {
//                Log.e("Cat Image Json", "Failed to fetch cat image: $statusCode")
//            }
//
//            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
//                Log.i("Cat Image Json", "Successfully fetched cat image: $json")
//                try {
//                    val resultsJSON : JSONObject = json.jsonObject as JSONObject
//                    imageUrl = resultsJSON.get("url").toString()
//
//                    Log.i("json", resultsJSON.toString())
//
//                } catch (e: JSONException) {
//                    Log.e("Cat Image Json", "Exception: $e")
//                }
//            }
//        })

        val radius = 40; // corner radius, higher value = more rounded
        val margin = 10; // crop margin, set to 0 for corners with no crop
        Glide.with(this@MainActivity)
            .load(R.drawable.sample)
            .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(radius,margin)))
            .into(binding?.ivDailyPhoto!!)


        var inputActivityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                    val newFood: Food = result.data?.getSerializableExtra("new food") as Food
                    foodList.add(newFood)
                    totalCaloriesAte += newFood.totalCalories?.toDouble()!!

                    binding?.tvSummary?.text = "Total calories needed per day: 2000 kcal\\nTotal calories you ate today: $totalCaloriesAte kcal"
                    lifecycleScope.launch(IO) {
                        (application as FoodApplication).db.foodDao().deleteAll()
                        (application as FoodApplication).db.foodDao().insertAll(foodList.map {
                            FoodEntity(
                                date = it.date,
                                foodName = it.foodName,
                                amount = it.amount,
                                caloriesUnit = it.caloriesUnit,
                                calories = it.totalCalories
                            )
                        })
                    }
                }
            }

        fun startInputActivity() {
            val intent = Intent(this@MainActivity, InputActivity::class.java)
            inputActivityResultLauncher.launch(intent)
        }

        binding?.btnInsert?.setOnClickListener {
            startInputActivity()
        }

        val foodAdapter = FoodAdapter(this@MainActivity, foodList)
        binding?.rvNutrition?.adapter = foodAdapter
        binding?.rvNutrition?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        lifecycleScope.launch {
            (application as FoodApplication).db.foodDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    Food(
                        entity.date,
                        entity.foodName,
                        entity.caloriesUnit,
                        entity.amount,
                        entity.calories
                    )
                }.also { mappedList ->
                    foodList.clear()
                    foodList.addAll(mappedList)
                    foodAdapter.notifyDataSetChanged()
                }
            }
        }

        binding?.btnDelete?.setOnClickListener {
            totalCaloriesAte = 0.0
            lifecycleScope.launch(IO) {
                (application as FoodApplication).db.foodDao().deleteAll()
            }
            binding?.tvSummary?.text = "Total calories needed per day: 2000 kcal\\nTotal calories you ate today: $totalCaloriesAte kcal"
        }
    }
}