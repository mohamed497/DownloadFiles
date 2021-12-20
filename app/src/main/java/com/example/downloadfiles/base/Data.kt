package com.example.downloadfiles.base

import com.example.downloadfiles.models.DownloadStatus
import com.example.downloadfiles.models.FileInfo


class Data {
    companion object {
        val listOfItems = arrayOf(
            FileInfo(
                1,
                "film new1",
                "uploads/2017/04/file_example_MP4_480_1_5MG.mp4",
                "path",
                DownloadStatus.DOWNLOADING
            ),
            FileInfo(
                2,
                "film new2",
                "uploads/2017/04/file_example_MP4_640_3MG.mp4",
                "path",
                DownloadStatus.DOWNLOADING
            ),
            FileInfo(
                3,
                "film new3",
                "uploads/2017/04/file_example_MP4_480_1_5MG.mp4",
                "path",
                DownloadStatus.DOWNLOADING
            ),
            FileInfo(
                4,
                "film new4",
                "uploads/2017/04/file_example_MP4_480_1_5MG.mp4",
                "path",
                DownloadStatus.DOWNLOADING
            ),
            FileInfo(
                5,
                "film new5",
                "uploads/2017/04/file_example_MP4_480_1_5MG.mp4",
                "path",
                DownloadStatus.DOWNLOADING
            ),
        )
    }
}
