package com.example.downloadfiles.ui.activity

import android.Manifest
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
import com.example.downloadfiles.service.DownloadFileService

class DownloadActivity : AppCompatActivity() {

    private var runtimePermission: RunTimePermission = RunTimePermission(this)
    private lateinit var fileAdapter: FileAdapter
    private lateinit var serviceIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        setupRecyclerview()
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
            fileAdapter = FileAdapter(Data.listOfItems) { file ->
                runtimePermission.requestPermission(listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    object : RunTimePermission.PermissionCallback {
                        override fun onGranted() {
                            serviceIntent =
                                Intent(this@DownloadActivity, DownloadFileService::class.java)
                            serviceIntent.putExtra(Constants.FILE_URL_KEY, file.url)
                            serviceIntent.putExtra(Constants.FILE_NAME_KEY, file.title)
                            startService(serviceIntent)

                        }

                        override fun onDenied() {
                            setupToast(getString(R.string.work_permission))
                        }
                    })
            }
            adapter = fileAdapter
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.PERMISSION_REQUEST)
            runtimePermission.onRequestPermissionsResult(grantResults)
    }

    private fun setupToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        if (this::serviceIntent.isInitialized)
        stopService(serviceIntent)
        super.onStop()
    }
}