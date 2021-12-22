package com.example.downloadfiles

import android.app.Application
import com.example.downloadfiles.di.repositoryModule
import com.example.downloadfiles.di.serviceModule
import com.example.downloadfiles.remote.di.networkKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DownloadApp : Application() {
    override fun onCreate() {
        super.onCreate()

        setupKoin()

    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@DownloadApp)
            modules(
                listOf(
                    serviceModule,
                    repositoryModule,
                    networkKoinModule
                )
            )
        }
    }
}