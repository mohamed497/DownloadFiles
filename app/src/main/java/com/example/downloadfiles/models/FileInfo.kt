package com.example.downloadfiles.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.downloadfiles.base.Constants

@Entity(tableName = Constants.TABLE_NAME)
data class FileInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val url: String,
    val filePath: String,
    val downloadStatus: DownloadStatus
)
