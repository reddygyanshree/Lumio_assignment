package com.example.tvassignment.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Listens to the device's network state (e.g. TV internet) via BroadcastReceiver
 * and exposes it as a StateFlow. Uses CONNECTIVITY_ACTION so the app reacts when
 * network is lost or restored (works on Android TV and other devices).
 */
object ConnectivityMonitor {

    private val _isConnected = MutableStateFlow(true)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private var appContext: Context? = null
    private var broadcastReceiver: BroadcastReceiver? = null

    /**
     * Call from Application.onCreate(). Registers a BroadcastReceiver for
     * connectivity changes and sets initial state from [ConnectivityHelper.isConnected].
     */
    fun start(context: Context) {
        val app = context.applicationContext
        appContext = app
        _isConnected.value = ConnectivityHelper.isConnected(app)

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                @Suppress("DEPRECATION")
                if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
                    val connected = ConnectivityHelper.isConnected(context.applicationContext)
                    if (connected != _isConnected.value) {
                        _isConnected.value = connected
                    }
                }
            }
        }
        broadcastReceiver = receiver
        @Suppress("DEPRECATION")
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            app.registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            @Suppress("DEPRECATION")
            app.registerReceiver(receiver, filter)
        }
    }

    /**
     * Call from Application.onTerminate() if needed, or leave registered for app lifetime.
     */
    fun stop() {
        broadcastReceiver?.let { appContext?.unregisterReceiver(it) }
        broadcastReceiver = null
        appContext = null
    }
}
