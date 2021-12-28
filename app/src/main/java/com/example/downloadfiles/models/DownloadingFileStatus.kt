package com.example.downloadfiles.models

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject

object DownloadingFileStatus {

    private val progressFile = PublishSubject.create<Pair<FileInfo?, Int>>()

    fun observeOnFileChange(): Observable<Pair<FileInfo?, Int>> {
        return progressFile.flatMap {
            return@flatMap Observable.just(it)
        }
    }

    fun emitNewProgressUpdates(progress: Int, fileInfo: FileInfo?) {
        progressFile.onNext(Pair(fileInfo, progress))
    }

}