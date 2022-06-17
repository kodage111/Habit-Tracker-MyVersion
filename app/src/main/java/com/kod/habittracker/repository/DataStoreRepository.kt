package com.kod.habittracker.repository

interface DataStoreRepository{
    fun saveFirstUsage(firstTime: Boolean)
    fun readFirstUsage(): Boolean
}