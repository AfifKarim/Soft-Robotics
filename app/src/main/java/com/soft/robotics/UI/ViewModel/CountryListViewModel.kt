package com.soft.robotics.UI.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soft.robotics.Data.Local.Entities.Country
import com.soft.robotics.Domain.UseCase.FetchLocalCountriesUseCase
import com.soft.robotics.Domain.UseCase.RefreshCountriesUseCase
import com.soft.robotics.Utils.Global
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    application: Application,
    private val refreshCountriesUseCase: RefreshCountriesUseCase,
    private val fetchLocalCountriesUseCase: FetchLocalCountriesUseCase
) : AndroidViewModel(application) {

    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> = _countries

    private val _networkStatus = MutableLiveData<Boolean>()
    val networkStatus: LiveData<Boolean> = _networkStatus

    init {
        _networkStatus.value = Global.isNetworkAvailable(application)
        Global.registerNetworkReceiver(application)

        Global.networkStatus.observeForever { isNetworkAvailable ->
            _networkStatus.value = isNetworkAvailable
            if (isNetworkAvailable) {
                refreshCountries()
            } else {
                fetchLocalCountries()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Global.unregisterNetworkReceiver(getApplication())
    }

    fun refreshCountries() {
        viewModelScope.launch {
            val success = refreshCountriesUseCase()
            if (success) {
                val freshCountries = fetchLocalCountriesUseCase()
                _countries.postValue(freshCountries)
            }
        }
    }

    fun fetchLocalCountries() {
        viewModelScope.launch {
            val localCountries = fetchLocalCountriesUseCase()
            _countries.postValue(localCountries)
        }
    }
}
