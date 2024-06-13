package com.soft.robotics.Domain.UseCase

import com.soft.robotics.Data.Local.Entities.Country
import com.soft.robotics.Data.Repository.CountryRepository
import javax.inject.Inject

class FetchLocalCountriesUseCase @Inject constructor(
    private val repository: CountryRepository
) {
    suspend operator fun invoke(): List<Country> {
        return repository.getLocalCountries()
    }
}