package com.example.tvassignment.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object ConnectivityHelper {

    /**
     * Returns true if the device has an active network that reports internet capability.
     * Does not require NET_CAPABILITY_VALIDATED, since many devices (e.g. Android TV, Ethernet)
     * don't set it even when internet is working. Falls back to activeNetworkInfo for devices
     * where activeNetwork is null but a connection exists.
     */
    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = cm.activeNetwork
            if (network != null) {
                val caps = cm.getNetworkCapabilities(network) ?: return fallbackConnected(cm)
                if (caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) return true
            }
            return fallbackConnected(cm)
        }
        return fallbackConnected(cm)
    }

    @Suppress("DEPRECATION")
    private fun fallbackConnected(cm: ConnectivityManager): Boolean {
        return cm.activeNetworkInfo?.isConnected == true
    }
}
