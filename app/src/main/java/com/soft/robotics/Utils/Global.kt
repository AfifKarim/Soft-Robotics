package com.soft.robotics.Utils

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.soft.robotics.Data.Local.Db.DB
import com.soft.robotics.Data.Remote.Api.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object Global {
    private val _networkStatus = MutableLiveData<Boolean>()
    val networkStatus: LiveData<Boolean> = _networkStatus

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            _networkStatus.postValue(isNetworkAvailable(context))
        }
    }

    fun registerNetworkReceiver(context: Context) {
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkReceiver, filter)
        _networkStatus.postValue(isNetworkAvailable(context))
    }

    fun unregisterNetworkReceiver(context: Context) {
        context.unregisterReceiver(networkReceiver)
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}