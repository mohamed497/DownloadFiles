package com.example.downloadfiles.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.downloadfiles.R
import com.example.downloadfiles.models.FileInfo
import kotlinx.android.synthetic.main.item_list.view.*

class FileAdapter(var files: Array<FileInfo>, val listener: (FileInfo) -> Unit) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))

    override fun getItemCount(): Int = files.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fileInfo = files[position]
        with(holder.itemView) {
            title.text = fileInfo.title
            setOnClickListener {
                it.isClickable = false
                listener(fileInfo)
            }
        }
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view)