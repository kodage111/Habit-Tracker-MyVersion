package com.kod.habittracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kod.habittracker.data.model.Habit
import com.kod.habittracker.logic.dao.HabitDAO


@Database(entities = [Habit::class], version = 1, exportSchema = true)
abstract class HabitDatabase: RoomDatabase() {

    abstract fun HabitDAO(): HabitDAO

    companion object{

        @Volatile       //Volatile means that anytime we are writing to this field, it would be made visible to other threads
        private var INSTANCE: HabitDatabase? = null

        fun getDatabase(context: Context): HabitDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): HabitDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                HabitDatabase::class.java,
                "notes_database"
            )
                .build()
        }
    }
}