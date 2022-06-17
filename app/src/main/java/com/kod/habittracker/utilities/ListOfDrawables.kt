package com.kod.habittracker.utilities

import com.kod.habittracker.R

interface ListOfDrawables {
    val listOutlineOfDrawables
        get() = arrayListOf(
            R.drawable.smoking_selected,
            R.drawable.beverage_selected,
            R.drawable.fastfood_selected,
            R.drawable.videos_selected,
            R.drawable.soccer_selected,
            R.drawable.fitness_selected,
            R.drawable.no_phone_selected,
            R.drawable.running_selected,
            R.drawable.sleep_selected
        )

    val listColoredDrawable
        get() = arrayListOf(
            R.drawable.ic_smoking_stop,
            R.drawable.ic_beverage,
            R.drawable.ic_fast_food,
            R.drawable.ic_video,
            R.drawable.ic_soccer,
            R.drawable.ic_fitness,
            R.drawable.ic_no_cell,
            R.drawable.ic_running,
            R.drawable.ic_sleep
        )
}