package com.example.downloadfiles.base

import com.example.downloadfiles.models.DownloadStatus
import com.example.downloadfiles.models.FileInfo


class Data {
    companion object {
        val listOfItems = arrayOf(
            FileInfo(
                1,
                "video3M",
                "https://file-examples-com.github.io/uploads/2017/04/file_example_MP4_640_3MG.mp4",
                "path",
                DownloadStatus.DOWNLOADING
            ),
            FileInfo(
                2,
                "Scene",
                "https://file-examples-com.github.io/uploads/2017/04/file_example_MP4_480_1_5MG.mp4",
                "path",
                DownloadStatus.DOWNLOADING
            ),
            FileInfo(
                3,
                "film",
                "https://file-examples-com.github.io/uploads/2017/04/file_example_MP4_480_1_5MG.mp4",
                "path",
                DownloadStatus.DOWNLOADING
            ),
            FileInfo(
                4,
                "movie",
                "https://file-examples-com.github.io/uploads/2017/04/file_example_MP4_1280_10MG.mp4",
                "path",
                DownloadStatus.DOWNLOADING
            ),
        )
    }
}
