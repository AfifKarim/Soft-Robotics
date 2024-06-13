package com.soft.robotics.Data.Repository

import android.app.Application
import com.soft.robotics.Data.Local.Dao.CountryDao
import com.soft.robotics.Data.Local.Db.DB
import com.soft.robotics.Data.Local.Entities.Country
import com.soft.robotics.Data.Remote.Api.ApiClient
import com.soft.robotics.Data.Remote.Api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CountryRepository @Inject constructor(
    private val countryDao: CountryDao,
    private val apiService: ApiService
) {
    suspend fun refreshCountries() {
        withContext(Dispatchers.IO) {
            try {
                val countryResponses = apiService.getCountries()
                val countries = countryResponses.map { Country(it.code, it.name) }
                countryDao.insertAll(countries)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getLocalCountries(): List<Country> {
        return withContext(Dispatchers.IO) {
            countryDao.getLocalCountries()
        }
    }
}