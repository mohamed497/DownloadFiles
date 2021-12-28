package com.example.downloadfiles.ui.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.downloadfiles.models.DownloadingFileStatus
import com.example.downloadfiles.models.FileInfo
import com.example.downloadfiles.utils.debug
import com.example.downloadfiles.utils.error
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy


class FileViewModel : ViewModel() {
    private val progressLiveData = MutableLiveData<Int>()
    private val fileInfoLiveData = MutableLiveData<FileInfo?>()

    fun observeOnFile(lifecycle: LifecycleOwner, fileInfo: Observer<FileInfo?>) {
        fileInfoLiveData.observe(lifecycle, fileInfo)
    }


    fun observeOnDownloadingProgress() {
        val observable: Observable<Pair<FileInfo?, Int>> =
            DownloadingFileStatus.observeOnFileChange()
        observable.subscribeBy(
            onNext = { result ->
                fileInfoLiveData.value = result.first
                progressLiveData.value = result.second
            }, onError = { throwable ->
                error("Error at observing", throwable)
            }, onComplete = {
                debug("Observe Completed")
            }
        )

    }

    fun getCurrentDownloadingItemProgress(): Int {
        return progressLiveData.value ?: 0
    }

}