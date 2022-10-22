package com.joshhn.bitfit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food_table")
    fun getAll(): Flow<List<FoodEntity>>

    @Query("SELECT calories FROM food_table")
    fun getTotalCalories(): Flow<List<Int>>

    @Insert
    fun insert(food: FoodEntity)

    @Insert
    fun insertAll(foodList: List<FoodEntity>)

    @Query("DELETE FROM food_table")
    fun deleteAll()
}