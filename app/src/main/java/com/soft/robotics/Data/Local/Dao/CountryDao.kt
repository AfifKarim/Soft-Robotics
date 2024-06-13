package com.soft.robotics.Data.Local.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soft.robotics.Data.Local.Entities.Country

@Dao
interface CountryDao {
    @Query("SELECT * FROM countries ORDER BY name ASC")
    fun getAllCountries(): LiveData<List<Country>>

    @Query("SELECT * FROM countries ORDER BY name ASC")
    fun getLocalCountries(): List<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<Country>)
}