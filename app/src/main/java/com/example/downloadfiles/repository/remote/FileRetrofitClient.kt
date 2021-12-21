package com.example.downloadfiles.repository.remote

import com.example.downloadfiles.base.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(GsonConverterFactory.create())
//    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//    .baseUrl(Constants.BASE_URL)
//    .build()
//
//object FileRetrofitClient {
//    val retrofitService: FileRetrofitService by lazy {
//        retrofit.create(FileRetrofitService::class.java)
//    }
//}