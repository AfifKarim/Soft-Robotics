//package com.soft.robotics.DI
//
//import android.app.Application
//import android.content.Context
//import androidx.room.Room
//import com.soft.robotics.Data.Local.Dao.CountryDao
//import com.soft.robotics.Data.Local.Db.DB
//import com.soft.robotics.Data.Remote.Api.ApiClient
//import com.soft.robotics.Data.Remote.Api.ApiService
//import com.soft.robotics.Data.Repository.CountryRepository
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//
//    @Provides
//    @Singleton
//    fun provideDatabase(@ApplicationContext context: Context): DB {
//        return Room.databaseBuilder(
//            context,
//            DB::class.java,
//            "app_database"
//        ).build()
//    }
//
//    @Provides
//    fun provideCountryDao(database: DB): CountryDao {
//        return database.countryDao()
//    }
//
//    @Provides
//    @Singleton
//    fun provideApiClient(): ApiClient {
//        return ApiClient.getInstance()
//    }
//
//    @Provides
//    fun provideApiService(apiClient: ApiClient): ApiService {
//        return apiClient.apiService
//    }
//
//    @Provides
//    fun provideCountryRepository(application: Application, countryDao: CountryDao, apiService: ApiService): CountryRepository {
//        return CountryRepository(application, countryDao, apiService)
//    }
//}
