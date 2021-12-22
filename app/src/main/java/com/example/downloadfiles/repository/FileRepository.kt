package com.example.downloadfiles.repository

import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.Response

interface FileRepository {
    fun getFiles(url: String): Observable<Response<ResponseBody>>
}