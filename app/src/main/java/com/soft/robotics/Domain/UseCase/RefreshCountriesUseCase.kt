package com.soft.robotics.Domain.UseCase

import com.soft.robotics.Data.Repository.CountryRepository
import javax.inject.Inject

class RefreshCountriesUseCase @Inject constructor(
    private val repository: CountryRepository
) {
    suspend operator fun invoke(): Boolean {
        return try {
            repository.refreshCountries()
            true
        } catch (e: Exception) {
            false
        }
    }
}