package com.joshhn.bitfit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LogFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_log, container, false)

        var ivDailyPhoto = view.findViewById<ImageView>(R.id.iv_daily_photo)
        var btnInsert = view.findViewById<Button>(R.id.btn_insert)

        val radius = 40; // corner radius, higher value = more rounded
        val margin = 10; // crop margin, set to 0 for corners with no crop
        Glide.with(this)
            .load(R.drawable.sample)
            .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(radius,margin)))
            .into(ivDailyPhoto)

        var inputActivityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val newFood: Food = result.data?.getSerializableExtra("new food") as Food

                lifecycleScope.launch(Dispatchers.IO) {
                    (activity?.application as FoodApplication).db.foodDao().insert(
                        FoodEntity(
                            date = newFood.date,
                            foodName = newFood.foodName,
                            amount = newFood.amount,
                            caloriesUnit = newFood.caloriesUnit,
                            calories = newFood.totalCalories
                        )
                    )
                }
            }
        }

        fun startInputActivity() {
            val intent = Intent(activity, InputActivity::class.java)
            inputActivityResultLauncher.launch(intent)
        }

        btnInsert.setOnClickListener {
            startInputActivity()
        }

        return view;
    }

    companion object {
        fun newInstance(): LogFragment {
            return LogFragment()
        }
    }
}