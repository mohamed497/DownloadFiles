package com.example.downloadfiles.repository.cache

import com.example.downloadfiles.App
import com.example.downloadfiles.models.FileInfo
import com.example.downloadfiles.repository.FileRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.Response

class FileCacheRepository(private val db: FileDatabase): FileRepository {
//    private val fileDao = FileDatabase.getInstance(App.instance).fileDao
    override fun insertFiles(files: List<FileInfo>): Completable {
        return db.fileDao.insertFiles(files)
    }

    override fun insertFile(file: FileInfo): Completable {
        return db.fileDao.insertFile(file)
    }

    override fun getSavedFiles(): Observable<List<FileInfo>> {
        return db.fileDao.getSavedFiles()
    }

    override fun getFiles(url: String): Observable<Response<ResponseBody>> {
         throw UnsupportedOperationException("Cant Get Update Top Files From Cache")
    }
//    override fun getFiles(file: FileInfo): Completable {
//         throw UnsupportedOperationException("Cant Get Update Top Files From Cache")
//    }
}