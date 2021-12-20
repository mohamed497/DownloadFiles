package com.example.downloadfiles.ui.viewmodel

import android.annotation.SuppressLint
import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.downloadfiles.repository.FileRepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import kotlin.math.pow
import kotlin.math.roundToInt

const val ARRAY_SIZE = 1024

class FileViewModel : ViewModel() {
    private val fileRepo = FileRepositoryImpl()
    fun downloadFile(url: String) {
        fileRepo.getFiles(url).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                saveFile(it.body())
            }, {
                Log.d("zxc", it.message!!)
            })
    }

    @SuppressLint("SdCardPath")
    private fun saveFile(response: ResponseBody?) {
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED != state) {
            return
        }
        var totalFileSize: Int
        var count: Int
        val data = ByteArray(ARRAY_SIZE)
        val fileSize = response?.contentLength()

        val input: InputStream = BufferedInputStream(response?.byteStream(), 1024 * 8)
        val output: OutputStream =
            FileOutputStream("/sdcard/myfile_${System.currentTimeMillis()}.mp4")
        var total = 0
        val startTime = System.currentTimeMillis()
        var timeCount = 1
        totalFileSize = (fileSize?.div(1024.0.pow(2.0)))?.toInt() ?: 0

        while (input.read(data).also { count = it } != -1) {
            total += count
            val current = (total / 1024.0.pow(2.0)).roundToInt().toDouble()
            val currentTime = System.currentTimeMillis() - startTime
            Log.d(FileViewModel::class.java.simpleName, totalFileSize.toString())
            if (currentTime > 1000 * timeCount) {
                val progress = ((total * 100) / (fileSize ?: 0L)).toInt()
                timeCount++
                Log.d(FileViewModel::class.java.simpleName, progress.toString())
            }
            output.write(data, 0, count)
        }
        output.flush()
        output.close()
        input.close()

    }
}