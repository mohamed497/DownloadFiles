package com.example.downloadfiles.remote.services

import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url


interface FileApiService{
    @GET
    @Streaming
    fun getFiles(@Url url: String): Observable<Response<ResponseBody>>
}
