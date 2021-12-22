package com.example.downloadfiles.service

import android.annotation.SuppressLint

object DownloadServiceConstants{
    const val NOTIFICATION_ID = 500

    @SuppressLint("SdCardPath")
    const val STORAGE = "/sdcard/"
    const val INPUT_SIZE = 1024 * 8
    const val PROGRESS_MAX = 100
    const val PROGRESS_INIT = 0
    const val NOTIFICATION_CHANNEL = "Notification_Channel"
    const val DEFAULT_DOWNLOAD_NAME = "file"
    const val ARRAY_SIZE = 1024
}