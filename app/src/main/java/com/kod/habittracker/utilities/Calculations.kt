package com.kod.habittracker.utilities

import java.sql.Timestamp
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Calculations {
    //todo: Change it so it returns a string to display to the textView of the habit item
    fun calculateTimeBetweenDates(startDate: String): String {

        val endDate = timeStampToString(System.currentTimeMillis())

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        sdf.isLenient = false
        val date1 = sdf.parse(startDate)
        val date2 = sdf.parse(endDate)

        var isNegative = false

        var difference = date2.time - date1.time
        if (difference < 0) {
            difference = -(difference)
            isNegative = true
        }

        val minutes = difference / 60 / 1000
        val hours = difference / 60 / 1000 / 60
        val days = (difference / 60 / 1000 / 60) / 24
        val months = (difference / 60 / 1000 / 60) / 24 / (365 / 12)
        val years = difference / 60 / 1000 / 60 / 24 / 365

        if (isNegative) {

            return when {
                minutes < 240 -> "Starts in $minutes minutes"
                hours < 48 -> "Starts in $hours hours"
                days < 61 -> "Starts in $days days"
                months < 24 -> "Starts in $months months"
                else -> "Starts in $years years"
            }
        }

        return when {
            minutes.toString().isEmpty() -> "now"
            minutes < 240 -> "$minutes minutes ago"
            hours < 48 -> "$hours hours ago"
            days < 61 -> "$days days ago"
            months < 24 -> "$months months ago"
            else -> "$years years ago"
        }
    }

    private fun timeStampToString(timeStamp: Long): String{
        val stamp = Timestamp(timeStamp)
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")

        //Returning the date
        return simpleDateFormat.format(Date(stamp.time)).toString()
    }

    fun cleanDate(_day: Int, _month: Int, _year: Int): String{
        var day = _day.toString()
        var month = _month.toString()
        val year = _year.toString()

        if(_day < 10) day = "0$_day"
        if(_month < 9) month = "0${_month+1}"

        return  "$day/$month/$year"
    }

    fun cleanTime(_hour: Int, _minutess: Int): String{
        var hour = _hour.toString()
        var minute = _minutess.toString()

        if(_hour < 10) hour = "0$_hour"
        if(_minutess < 10) minute = "0$_minutess"

        return  "$hour:$minute"
    }

    fun checkValues(title: String, description: String, timeStamp: String, drawable: Int): Boolean = !(title.isBlank() || description.isBlank() || timeStamp.isBlank() || drawable == 0)
}