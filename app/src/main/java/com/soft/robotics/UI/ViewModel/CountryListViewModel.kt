package com.soft.robotics.UI.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soft.robotics.Data.Local.Entities.Country
import com.soft.robotics.Data.Repository.CountryRepository
import com.soft.robotics.Utils.Global
import kotlinx.coroutines.launch

class CountryListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CountryRepository = CountryRepository(application)

    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> = _countries

    private val _networkStatus = MutableLiveData<Boolean>()
    val networkStatus: LiveData<Boolean> = _networkStatus

    private val _localCountries = MutableLiveData<List<Country>>()
    val localCountries: LiveData<List<Country>> = _localCountries

    init {
        _networkStatus.value = Global.isNetworkAvailable(application)
        Global.registerNetworkReceiver(application)

        // Observe network status changes
        Global.networkStatus.observeForever { isNetworkAvailable ->
            _networkStatus.value = isNetworkAvailable
            if (isNetworkAvailable) {
                refreshCountries()
                viewModelScope.launch {
                    val hasChanges = Global.checkForDataChanges(application)
                    if (hasChanges) {
                        refreshCountries()
                    }
                }
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
            val fetchedCountries = repository.refreshCountries()
            _countries.postValue(fetchedCountries)
        }
    }

    fun fetchLocalCountries() {
        viewModelScope.launch {
            val localCountries = repository.getLocalCountries()
            _countries.postValue(localCountries)
        }
    }
}
