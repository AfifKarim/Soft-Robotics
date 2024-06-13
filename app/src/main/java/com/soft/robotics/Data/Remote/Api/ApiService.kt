package com.soft.robotics.Data.Remote.Api

import com.soft.robotics.Data.Remote.Responses.CountryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("almost/7748738/raw/575f851d945e2a9e6859fb2308e95a3697bea115/countries.json")
    suspend fun getCountries(): List<CountryResponse>
}