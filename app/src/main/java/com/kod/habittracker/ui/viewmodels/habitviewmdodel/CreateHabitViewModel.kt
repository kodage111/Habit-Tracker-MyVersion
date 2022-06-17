package com.kod.habittracker.ui.viewmodels.habitviewmdodel

import androidx.lifecycle.ViewModel
import java.util.*

class CreateHabitViewModel: ViewModel() {

    var day : Int = 0
    var month: Int = 0
    var year: Int = 0
    var minute: Int = 0
    var hour: Int = 0
    var cleantime = " "
    var cleanDate = " "
    var title = " "
    var description = " "
    var drawableSelected = 0
    var txTimeSelected = " "
    var txDateSelected = " "
    private val cal =Calendar.getInstance()

    fun getCurrentDateCalendar(){
        this.day = cal[Calendar.DAY_OF_MONTH]
        this.month = cal[Calendar.MONTH]
        this.year = cal[Calendar.YEAR]
    }

    fun getCurrentTimeCalendar(){
        this.hour = cal[Calendar.HOUR_OF_DAY]
        this.minute = cal[Calendar.MINUTE]
    }
}