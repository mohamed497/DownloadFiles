package com.example.downloadfiles.di

import android.app.Service
import com.example.downloadfiles.repository.FileRepositoryImpl

import com.example.downloadfiles.remote.FIleRemoteRepository
import com.example.downloadfiles.service.DownloadFileService
import org.koin.dsl.module

val serviceModule = module {
    single<Service> { DownloadFileService() }
}

val repositoryModule = module {
    single { FIleRemoteRepository(downloadService = get()) }
    single { FileRepositoryImpl( remoteRepo = get()) }

}
