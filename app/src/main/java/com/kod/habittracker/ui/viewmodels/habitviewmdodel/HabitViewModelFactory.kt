package com.kod.habittracker.ui.viewmodels.habitviewmdodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HabitViewModelFactory(var application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HabitViewModel::class.java)) return HabitViewModel(application) as T
        throw IllegalArgumentException ("UnknownViewModel class")
    }
}