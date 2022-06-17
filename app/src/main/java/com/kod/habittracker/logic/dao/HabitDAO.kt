package com.kod.habittracker.logic.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kod.habittracker.data.model.Habit

@Dao
interface HabitDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHabit(habit: Habit)

    @Update
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("DELETE FROM habit_table")
    suspend fun deleteAllHabits()

    @Query("SELECT * FROM habit_table ORDER BY id DESC")
    fun readAllHabits(): LiveData<List<Habit>>
}