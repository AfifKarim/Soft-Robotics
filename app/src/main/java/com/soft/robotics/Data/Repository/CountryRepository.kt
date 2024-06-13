package com.soft.robotics.Data.Repository

import android.app.Application
import com.soft.robotics.Data.Local.Dao.CountryDao
import com.soft.robotics.Data.Local.Db.DB
import com.soft.robotics.Data.Local.Entities.Country
import com.soft.robotics.Data.Remote.Api.ApiClient
import com.soft.robotics.Data.Remote.Api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CountryRepository(private val application: Application) {

    private val countryDao: CountryDao = DB.getInstance(application).countryDao()
    private val apiService: ApiService = ApiClient.getInstance().apiService

    suspend fun refreshCountries(): List<Country> {
        return try {
            val countryResponses = apiService.getCountries()
            val countries = countryResponses.map { Country(it.code, it.name) }
            countryDao.insertAll(countries)
            countries
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getLocalCountries(): List<Country> {
        return withContext(Dispatchers.IO) {
            countryDao.getLocalCountries()
        }
    }
}
