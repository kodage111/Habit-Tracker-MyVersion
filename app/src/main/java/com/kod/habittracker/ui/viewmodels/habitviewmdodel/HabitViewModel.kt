package com.kod.habittracker.ui.viewmodels.habitviewmdodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kod.habittracker.data.database.HabitDatabase
import com.kod.habittracker.data.model.Habit
import com.kod.habittracker.logic.repository.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Habit>>
    private val repository: HabitRepository

    init {
        val habitDAO = HabitDatabase.getDatabase(application).HabitDAO()
        repository = HabitRepository(habitDAO)
        this.readAllData = repository.readAllData
    }

    fun addHabit(habit: Habit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHabit(habit)
        }
    }

    fun updateHabit(habit: Habit){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateHabit(habit)
        }
    }

    fun deleteHabit(habit: Habit){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteHabit(habit)
        }
    }

    fun deleteAllHabits(){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteAllHabits()
        }
    }
}