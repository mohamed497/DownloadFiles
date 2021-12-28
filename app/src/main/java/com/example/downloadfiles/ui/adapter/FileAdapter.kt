package com.example.downloadfiles.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.downloadfiles.R
import com.example.downloadfiles.models.DownloadStatus
import com.example.downloadfiles.models.FileInfo
import com.example.downloadfiles.utils.*
import kotlinx.android.synthetic.main.item_list.view.*

class FileAdapter(private var files: Array<FileInfo>, private val callback: Callback) :
    RecyclerView.Adapter<FileAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))

    override fun getItemCount(): Int = files.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fileInfo = files[position]
        with(holder.itemView) {
            title.text = fileInfo.title
            when {
                isCurrentFileDownloading(fileInfo) -> {
                    progressBar.toVisible()
                    progressBar.isIndeterminate = false
                    progressBar.progress = callback.getCurrentDownloadingItemProgress()
                }
                fileInfo.downloadStatus == DownloadStatus.PENDING -> {
                    progressBar.toVisible()
                    progressBar.isIndeterminate = true
                }
                fileInfo.downloadStatus == DownloadStatus.DOWNLOADED -> {
                    progressBar.toGone()
                }
                else -> {
                    progressBar.toGone()
                }
            }
            setOnClickListener { view ->
                progressBar.isVisible = true
                view.checkClickable()
                callback.onItemClicked(fileInfo, position)
            }
        }
    }

    private fun isCurrentFileDownloading(fileInfo: FileInfo): Boolean =
        callback.isCurrentItemDownloading(fileInfo)

    fun findItemByUrl(url: String?): FileInfo? {
        var fileInfo: FileInfo? = null
        val size = files.size
        for (i in 0 until size) {
            if (url == files[i].url) {
                fileInfo = files[i]
                fileInfo.downloadStatus = DownloadStatus.DOWNLOADING
            }
        }
        return fileInfo
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface Callback {

        fun isCurrentItemDownloading(fileInfo: FileInfo): Boolean

        fun onItemClicked(fileInfo: FileInfo, position: Int)

        fun getCurrentDownloadingItemProgress(): Int
    }
}


