package com.kod.habittracker.logic.repository

import androidx.lifecycle.LiveData
import com.kod.habittracker.data.model.Habit
import com.kod.habittracker.logic.dao.HabitDAO

class HabitRepository(private val habitDAO: HabitDAO) {

    val readAllData: LiveData<List<Habit>> = habitDAO.readAllHabits()

    suspend fun updateHabit(user: Habit){
        habitDAO.updateHabit(user)
    }

    suspend fun deleteHabit(user: Habit){
        habitDAO.deleteHabit(user)
    }

    suspend fun deleteAllHabits(){
        habitDAO.deleteAllHabits()
    }

    suspend fun addHabit(user: Habit){
        habitDAO.addHabit(user)
    }
}