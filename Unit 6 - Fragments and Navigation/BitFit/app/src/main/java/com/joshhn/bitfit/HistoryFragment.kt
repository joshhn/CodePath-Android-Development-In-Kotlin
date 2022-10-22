package com.joshhn.bitfit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HistoryFragment : Fragment() {

    private var foodList: MutableList<Food> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =  inflater.inflate(R.layout.fragment_history, container, false)

        var rvNutrition = view.findViewById<RecyclerView>(R.id.rv_nutrition)
        var btnDelete = view.findViewById<Button>(R.id.btn_delete)

        val foodAdapter = FoodAdapter(activity!!, foodList)
        rvNutrition?.adapter = foodAdapter
        rvNutrition?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        lifecycleScope.launch {
            (activity!!.application as FoodApplication).db.foodDao().getAll().collect { databaseList ->
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

        btnDelete?.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                (activity!!.application as FoodApplication).db.foodDao().deleteAll()
            }
        }

        return view
    }

    companion object {
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }
}