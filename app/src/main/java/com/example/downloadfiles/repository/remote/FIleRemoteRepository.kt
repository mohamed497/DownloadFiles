package com.example.downloadfiles.repository.remote

import com.example.downloadfiles.models.FileInfo
import com.example.downloadfiles.repository.FileRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.Response

class FIleRemoteRepository(private val downloadService:FileRetrofitService): FileRepository {
//    private val downloadService = FileRetrofitClient.retrofitService

    override fun insertFiles(files: List<FileInfo>): Completable {
        throw UnsupportedOperationException("You can not get insert files in Remote layer")
    }

    override fun insertFile(file: FileInfo): Completable {
        throw UnsupportedOperationException("You can not get insert file in Remote layer")
    }

    override fun getSavedFiles(): Observable<List<FileInfo>> {
        throw UnsupportedOperationException("You can not get saved files in Remote layer")
    }

    override fun getFiles(url: String): Observable<Response<ResponseBody>> {
        return downloadService.getFiles(url)
    }
//    override fun getFiles(file: FileInfo): Completable {
//        return downloadService.getFiles(file)
//    }
}