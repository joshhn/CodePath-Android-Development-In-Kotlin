package com.joshhn.bitfit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.joshhn.bitfit.databinding.ActivityInputBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class InputActivity : AppCompatActivity() {

    private var binding: ActivityInputBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnAdd?.setOnClickListener {
            var foodName: String = binding?.etFoodName?.text.toString()
            var amount: Double = binding?.etAmount?.text.toString().toDouble()
            var caloriesUnit: Double = binding?.etCaloriesUnit?.text.toString().toDouble()
            var calories: Int = (amount * caloriesUnit/100).toInt()

            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formattedDate: String = current.format(formatter).toString()
            var newFood = Food(formattedDate,foodName,caloriesUnit,amount,calories)
            val resultIntent = Intent()
            resultIntent.putExtra("new food", newFood)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}