package com.example.downloadfiles.remote

import com.example.downloadfiles.repository.FileRepository
import com.example.downloadfiles.remote.services.FileApiService
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.Response

class FIleRemoteRepository(private val downloadService: FileApiService): FileRepository {

    override fun getFiles(url: String): Observable<Response<ResponseBody>> {
        return downloadService.getFiles(url)
    }

}