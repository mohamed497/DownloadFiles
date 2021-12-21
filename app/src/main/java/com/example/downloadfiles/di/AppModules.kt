package com.example.downloadfiles.di

import android.app.Application
import android.app.Service
import androidx.room.Room
import com.example.downloadfiles.base.Constants
import com.example.downloadfiles.repository.FileRepositoryImpl
import com.example.downloadfiles.repository.cache.FileCacheRepository
import com.example.downloadfiles.repository.cache.FileDatabase
import com.example.downloadfiles.repository.remote.FIleRemoteRepository
import com.example.downloadfiles.repository.remote.FileRetrofitService
import com.example.downloadfiles.service.DownloadFileService
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val serviceModule = module {
    single<Service> { DownloadFileService(fileRepo = get()) }
}

val repositoryModule = module {
    single { FIleRemoteRepository(downloadService = get()) }
    single { FileCacheRepository(db = get()) }
    single { FileRepositoryImpl(cacheRepo = get(), remoteRepo = get()) }

}

val serviceAPIModule = module {
    fun getRetrofitBuilder(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
    }
    single { getRetrofitBuilder() }
    fun getServiceAPIInstance(retrofit: Retrofit): FileRetrofitService {
        return retrofit.create(FileRetrofitService::class.java)
    }
    single { getServiceAPIInstance(retrofit = get()) }
}
val databaseModule = module {
    fun getDatabaseInstance(application: Application): FileDatabase {
        return Room.databaseBuilder(
            application,
            FileDatabase::class.java, Constants.DATABASE_NAME
        )
            .build()
    }
    single { getDatabaseInstance(androidApplication()) }
}