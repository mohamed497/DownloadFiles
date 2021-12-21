package com.example.downloadfiles

import android.app.Application
import com.example.downloadfiles.di.databaseModule
import com.example.downloadfiles.di.repositoryModule
import com.example.downloadfiles.di.serviceAPIModule
import com.example.downloadfiles.di.serviceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DownloadApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@DownloadApp)
            modules(listOf(
                serviceModule,
                repositoryModule,
                serviceAPIModule,
                databaseModule
            ))
        }
    }
}