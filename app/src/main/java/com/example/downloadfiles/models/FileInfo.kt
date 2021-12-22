package com.example.downloadfiles.models

data class FileInfo(
    val id: Int,
    val title: String,
    val url: String,
    val filePath: String,
    val downloadStatus: DownloadStatus
)
