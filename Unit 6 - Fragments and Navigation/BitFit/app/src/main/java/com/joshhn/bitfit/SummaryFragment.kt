package com.joshhn.bitfit

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class SummaryFragment : Fragment() {

    private var totalCaloriesAte:Int = 0
    private var minCalories:Int = 999999999
    private var maxCalories:Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_summary, container, false)

        val tvSummary = view.findViewById<TextView>(R.id.tv_summary)

        lifecycleScope.launch {
            (activity!!.application as FoodApplication).db.foodDao().getTotalCalories()
                .collect() { totalCalories ->

                    totalCaloriesAte = 0
                    minCalories = 999999999
                    maxCalories = 0
                    for (calories in totalCalories){
                        totalCaloriesAte += calories
                        if(calories > maxCalories){
                            maxCalories = calories
                        }
                        if(calories < minCalories){
                            minCalories = calories
                        }
                    }
                }
        }
        Handler().postDelayed({
            tvSummary?.text = "Total calories needed per day: 2000 kcal\n" +
                    "\nTotal calories you ate today: $totalCaloriesAte kcal\n" +
                    "\nMax calories taken: $maxCalories kcal\n" +
                    "\nMin calories taken: $minCalories kcal"
        }, 500)


        return view
    }

    companion object {
        fun newInstance(): SummaryFragment {
            return SummaryFragment()
        }
    }
}