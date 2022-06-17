package com.kod.habittracker.repository

import android.content.SharedPreferences

class DataStoreRepositoryImp(private val sharedPrefs: SharedPreferences) : DataStoreRepository{
    companion object{
        private const val SHARED_PREFERENCE_FIRST_TIME = "first_time"
    }

    override fun readFirstUsage(): Boolean {
        return sharedPrefs.getBoolean(SHARED_PREFERENCE_FIRST_TIME, true)
    }
    override fun saveFirstUsage(firstTime: Boolean){
        sharedPrefs.edit().putBoolean(SHARED_PREFERENCE_FIRST_TIME, firstTime).apply()
    }
}