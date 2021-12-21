package com.example.downloadfiles.repository

import com.example.downloadfiles.models.FileInfo
import com.example.downloadfiles.repository.cache.FileCacheRepository
import com.example.downloadfiles.repository.remote.FIleRemoteRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response

class FileRepositoryImpl(
    private val cacheRepo: FileCacheRepository,
    private val remoteRepo: FIleRemoteRepository
) : FileRepository {

//    private val cacheRepo: FileRepository = FileCacheRepository()
//    private val remoteRepo: FileRepository = FIleRemoteRepository()

    override fun insertFiles(files: List<FileInfo>): Completable {
        return cacheRepo.insertFiles(files)
    }

    override fun insertFile(file: FileInfo): Completable {
        return cacheRepo.insertFile(file)
    }

    override fun getSavedFiles(): Observable<List<FileInfo>> {
        return cacheRepo.getSavedFiles()
    }

    override fun getFiles(url: String): Observable<Response<ResponseBody>> {
        return remoteRepo.getFiles(url)
    }
//    override fun getFiles(file: FileInfo): Completable {
//        return remoteRepo.getFiles(file)
//    }
}