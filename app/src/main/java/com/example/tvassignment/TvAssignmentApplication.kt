package com.example.tvassignment

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.example.tvassignment.data.remote.OkHttpProvider
import com.example.tvassignment.util.ConnectivityMonitor
import org.conscrypt.Conscrypt
import java.security.Security

/**
 * Application class: Conscrypt for SSL, app context for DB, and a shared
 * ImageLoader so Coil uses the same OkHttpClient as the API (fixes image loading).
 */
class TvAssignmentApplication : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        instance = this
        installConscrypt()
        Coil.setImageLoader(this)
        ConnectivityMonitor.start(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        ConnectivityMonitor.stop()
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .okHttpClient { OkHttpProvider.client }
            .crossfade(true)
            .build()
    }

    companion object {
        lateinit var instance: TvAssignmentApplication
            private set
    }

    private fun installConscrypt() {
        try {
            Security.insertProviderAt(Conscrypt.newProvider(), 1)
        } catch (e: Exception) {
            // Conscrypt may already be installed or unavailable; default provider is used
        }
    }
}
