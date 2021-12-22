package com.example.downloadfiles.remote.di

import com.example.downloadfiles.remote.services.FileApiService
import com.example.downloadfiles.remote.client.RetrofitClient
import org.koin.dsl.module
import retrofit2.Retrofit

val networkKoinModule = module {

    single { RetrofitClient.createRetrofitInstance() }

    single {
        RetrofitClient.createServiceClass(
            get(),
            FileApiService::class.java
        )
    }

}