package com.example.downloadfiles.ui.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.downloadfiles.models.DownloadingFileStatus
import com.example.downloadfiles.utils.debug
import com.example.downloadfiles.utils.error
import io.reactivex.rxjava3.core.Observable


class FileViewModel : ViewModel() {
    private val progressLiveData = MutableLiveData<Int>()


    fun observeOnProgress(lifecycle: LifecycleOwner, progress: Observer<Int>) {
        progressLiveData.observe(lifecycle, progress)
    }


    fun observeOnDownloadingProgress() {
        val observable: Observable<Int> = DownloadingFileStatus.observeOnChangingProgress()
        observable.subscribe({ progress ->
            progressLiveData.value = progress
        }, { throwable ->
            error("error at getting progress", throwable)
        }, {
            debug("complete view progress")
        })

    }

}