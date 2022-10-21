package com.joshhn.bitfit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter (private val context: Context, private val foodList: List<Food>) :
    RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_nutrition, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO: Get the individual article and bind to holder
        val food = foodList[position]
        holder.bind(food)
    }

    override fun getItemCount() = foodList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private var tvFoodName: TextView = itemView.findViewById(R.id.tv_foodName)
        private var tvDate: TextView = itemView.findViewById(R.id.tv_date)
        private var tvCalories: TextView = itemView.findViewById(R.id.tv_calories)


        fun bind(food: Food) {
            tvFoodName.text = food.foodName
            tvDate.text = food.date
            tvCalories.text = food.totalCalories.toString()
        }
    }
}