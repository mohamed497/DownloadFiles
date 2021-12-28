package com.example.downloadfiles.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.downloadfiles.R
import com.example.downloadfiles.base.Constants
import com.example.downloadfiles.base.Data
import com.example.downloadfiles.base.RunTimePermission
import com.example.downloadfiles.ui.adapter.FileAdapter
import kotlinx.android.synthetic.main.activity_download.*
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.downloadfiles.models.DownloadStatus
import com.example.downloadfiles.models.DownloadingFileStatus
import com.example.downloadfiles.models.FileInfo
import com.example.downloadfiles.service.DownloadFileService
import com.example.downloadfiles.ui.viewmodel.FileViewModel
import com.example.downloadfiles.utils.debug
import com.example.downloadfiles.utils.error
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import org.koin.android.viewmodel.ext.android.viewModel

class DownloadActivity : AppCompatActivity(), FileAdapter.Callback {

    private var runtimePermission: RunTimePermission = RunTimePermission(this)
    private lateinit var fileAdapter: FileAdapter
    private val viewModel: FileViewModel by viewModel()
    private val callback: FileAdapter.Callback = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)

        setupAdapter()
        setupRecyclerview()
        viewModel.observeOnDownloadingProgress()
        observeOnFile()
    }

    private fun setupAdapter() {
        fileAdapter = FileAdapter(Data.listOfItems, callback)
    }

    private fun setupRecyclerview() {
        with(recyclerView) {
            layoutManager = LinearLayoutManager(this@DownloadActivity)
            DividerItemDecoration(
                context,
                (layoutManager as LinearLayoutManager).orientation
            ).apply {
                addItemDecoration(this)
            }
            adapter = fileAdapter
        }
    }

    private fun startService(file: FileInfo) {
        val serviceIntent =
            Intent(this@DownloadActivity, DownloadFileService::class.java)
        serviceIntent.putExtra(Constants.FILE_KEY, file)
        startService(serviceIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.PERMISSION_REQUEST)
            runtimePermission.onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun setupToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeOnFile() {
        viewModel.observeOnFile(this, { file ->
            fileAdapter.findItemByUrl(file?.url)
            fileAdapter.notifyDataSetChanged()
        })
    }

    override fun isCurrentItemDownloading(fileInfo: FileInfo): Boolean {
        return fileInfo.downloadStatus == DownloadStatus.DOWNLOADING
    }

    override fun onItemClicked(fileInfo: FileInfo, position: Int) {
        runtimePermission.requestPermission(listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            object : RunTimePermission.PermissionCallback {
                override fun onGranted(requestCode: Int) {
                    startService(fileInfo)
                }

                override fun onDenied(requestCode: Int) {
                    setupToast(getString(R.string.work_permission))
                }
            })
    }

    override fun getCurrentDownloadingItemProgress(): Int {
        return viewModel.getCurrentDownloadingItemProgress()
    }

}