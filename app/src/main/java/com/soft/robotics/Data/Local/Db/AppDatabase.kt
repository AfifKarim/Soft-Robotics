package com.soft.robotics.Data.Local.Db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.soft.robotics.Data.Local.Dao.CountryDao
import com.soft.robotics.Data.Local.Entities.Country

@Database(entities = [Country::class], version = 1, exportSchema = false)
abstract class DB : RoomDatabase() {

    abstract fun countryDao(): CountryDao

    companion object {
        @Volatile
        private var INSTANCE: DB? = null

        fun getInstance(context: Context): DB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DB::class.java,
                    "country_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}