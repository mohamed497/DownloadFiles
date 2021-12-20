package com.example.downloadfiles.repository.remote

import com.example.downloadfiles.models.FileInfo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url


interface FileRetrofitService{
    @GET
    @Streaming
    fun getFiles(@Url url: String): Observable<Response<ResponseBody>>
//    fun getFiles(@Url fileInfo: FileInfo): Completable
}
