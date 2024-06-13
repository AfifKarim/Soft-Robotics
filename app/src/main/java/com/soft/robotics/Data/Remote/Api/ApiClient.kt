package com.soft.robotics.Data.Remote.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService
        get() = retrofit.create(ApiService::class.java)

    companion object {
        private const val BASE_URL = "https://gist.githubusercontent.com/"

        @Volatile
        private var instance: ApiClient? = null

        fun getInstance(): ApiClient {
            return instance ?: synchronized(this) {
                instance ?: ApiClient().also { instance = it }
            }
        }
    }
}