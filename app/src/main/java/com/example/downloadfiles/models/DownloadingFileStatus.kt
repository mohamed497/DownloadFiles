package com.example.downloadfiles.models

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

object DownloadingFileStatus {

    val progressFile = PublishSubject.create<Int>()
    fun observeOnChangingProgress(): Observable<Int> {
        return progressFile
    }

}