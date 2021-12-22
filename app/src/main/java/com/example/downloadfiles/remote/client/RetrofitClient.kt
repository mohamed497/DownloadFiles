package com.example.downloadfiles.remote.client

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    fun createRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(NetworkConstants.BASE_URL)
            .build()
    }

    fun <T> createServiceClass(retrofit: Retrofit, classObj: Class<T>): T {
        return retrofit.create(classObj)
    }
}