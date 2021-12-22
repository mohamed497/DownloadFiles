package com.example.downloadfiles.repository

import com.example.downloadfiles.remote.FIleRemoteRepository
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.Response

class FileRepositoryImpl(
    private val remoteRepo: FIleRemoteRepository
) : FileRepository {

    override fun getFiles(url: String): Observable<Response<ResponseBody>> {
        return remoteRepo.getFiles(url)
    }

}