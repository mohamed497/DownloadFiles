package com.example.downloadfiles.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FileInfo(
    val id: Int,
    val title: String,
    val url: String,
    val filePath: String,
    var downloadStatus: DownloadStatus,
) : Parcelable


