package com.example.myapplication.ui.dashboard

import android.net.wifi.ScanResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment!"
    }
    val text: LiveData<String> = _text

    private val _wifiNetworks = MutableLiveData<List<ScanResult>>()
    val wifiNetworks: LiveData<List<ScanResult>> = _wifiNetworks

    fun setWifiNetworks(networks: List<ScanResult>) {
        _wifiNetworks.value = networks
    }
}