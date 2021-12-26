package com.example.downloadfiles.di

import android.app.Service
import com.example.downloadfiles.repository.FileRepositoryImpl

import com.example.downloadfiles.remote.FIleRemoteRepository
import com.example.downloadfiles.service.DownloadFileService
import com.example.downloadfiles.ui.viewmodel.FileViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { FileViewModel() }
}
val serviceModule = module {
    single<Service> { DownloadFileService() }
}

val repositoryModule = module {
    single { FIleRemoteRepository(downloadService = get()) }
    single { FileRepositoryImpl( remoteRepo = get()) }

}
