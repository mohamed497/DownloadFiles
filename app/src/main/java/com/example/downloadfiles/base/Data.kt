package com.example.downloadfiles.base

import com.example.downloadfiles.models.DownloadStatus
import com.example.downloadfiles.models.FileInfo


object Data {
    val listOfItems = arrayOf(
        FileInfo(
            1,
            "video3M",
            "https://file-examples-com.github.io/uploads/2017/04/file_example_MP4_640_3MG.mp4",
            "path",
            DownloadStatus.NOT_DOWNLOADED
        ),
        FileInfo(
            2,
            "Scene",
            "https://file-examples-com.github.io/uploads/2017/04/file_example_MP4_480_1_5MG.mp4",
            "path",
            DownloadStatus.NOT_DOWNLOADED
        ),
        FileInfo(
            3,
            "movie",
            "https://file-examples-com.github.io/uploads/2017/04/file_example_MP4_1280_10MG.mp4",
            "path",
            DownloadStatus.NOT_DOWNLOADED
        ),
    )
}

