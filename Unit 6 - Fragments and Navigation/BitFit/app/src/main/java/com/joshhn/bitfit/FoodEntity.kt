package com.joshhn.bitfit

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_table")
data class FoodEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "foodName") val foodName: String?,
    @ColumnInfo(name = "caloriesUnit") val caloriesUnit: Double?,
    @ColumnInfo(name = "amount") val amount: Double?,
    @ColumnInfo(name = "calories") val calories: Int?
)