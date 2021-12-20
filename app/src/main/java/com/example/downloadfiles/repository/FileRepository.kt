package com.example.downloadfiles.repository

import com.example.downloadfiles.models.FileInfo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.Response

interface FileRepository {
    fun insertFiles(files: List<FileInfo>): Completable
    fun insertFile(file: FileInfo): Completable
    fun getSavedFiles(): Observable<List<FileInfo>>
    fun getFiles(url: String): Observable<Response<ResponseBody>>
//    fun getFiles(file: FileInfo): Completable
}