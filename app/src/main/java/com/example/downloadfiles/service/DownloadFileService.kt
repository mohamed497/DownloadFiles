package com.example.downloadfiles.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.os.IBinder
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.downloadfiles.R
import com.example.downloadfiles.base.Constants
import com.example.downloadfiles.repository.FileRepositoryImpl
import com.example.downloadfiles.utils.debug
import com.example.downloadfiles.utils.error
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import org.koin.android.ext.android.inject
import java.io.BufferedInputStream
import java.io.FileOutputStream


class DownloadFileService : Service() {

    private val fileRepo by inject<FileRepositoryImpl>()
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationBuilder: NotificationCompat.Builder


    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        val url = intent?.getStringExtra(Constants.FILE_URL_KEY)
        val name = intent?.getStringExtra(Constants.FILE_NAME_KEY)

        setupNotification()
        observeOnDownloadingFile(url, name)
        Toast.makeText(this, "Downloading", Toast.LENGTH_SHORT).show()

        return START_STICKY
    }

    private fun observeOnDownloadingFile(url: String?, name: String?) {

        fileRepo.getFiles(url ?: "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = { response ->
                saveFile(response.body(), name ?: DownloadServiceConstants.DEFAULT_DOWNLOAD_NAME)
                stopSelf()
            },
                onError = { throwable ->
                    error("error in downloading file", throwable)
                },
                onComplete = {
                    displayNotification(DownloadServiceConstants.PROGRESS_MAX, name ?: DownloadServiceConstants.DEFAULT_DOWNLOAD_NAME)
                    debug("Download Completed")
                })

    }

    private fun setupNotification() {
        notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationBuilder =
            NotificationCompat.Builder(applicationContext, DownloadServiceConstants.NOTIFICATION_CHANNEL)
    }

    private fun saveFile(response: ResponseBody?, name: String) {
        createNotificationChannel()
        displayNotification(progress = DownloadServiceConstants.PROGRESS_INIT, name = name)
        checkExternalStorageState()
        var count: Int
        val data = ByteArray(DownloadServiceConstants.ARRAY_SIZE)
        val fileSize = response?.contentLength()
        val input = BufferedInputStream(response?.byteStream(), DownloadServiceConstants.INPUT_SIZE)
        val output = FileOutputStream("${DownloadServiceConstants.STORAGE}${name}${System.currentTimeMillis()}.mp4")
        var total = 0

        while (input.read(data).also { count = it } != -1) {
            total += count
            val progress = ((total * 100) / (fileSize ?: 1L)).toInt()
            displayNotification(progress = progress, name = name)
            output.write(data, 0, count)
        }
        output.flush()
        output.close()
        input.close()

    }

    private fun checkExternalStorageState() {
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED != state) {
            return
        }
    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                DownloadServiceConstants.NOTIFICATION_CHANNEL,
                DownloadServiceConstants.NOTIFICATION_CHANNEL,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(false)
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("RemoteViewLayout")
    private fun displayNotification(progress: Int, name: String) {
        val remoteView = RemoteViews(applicationContext.packageName, R.layout.custom_notif)
        remoteView.setImageViewResource(R.id.iv_notif, R.drawable.ic_insert_drive_file_black_24dp)
        remoteView.setTextViewText(
            R.id.tv_notif_progress,
            "$progress complete)"
        )
        remoteView.setTextViewText(R.id.tv_notif_title, "Downloading $name")
        remoteView.setProgressBar(
            R.id.pb_notif,
            DownloadServiceConstants.PROGRESS_MAX,
            progress,
            false
        )
        notificationBuilder
            .setContent(remoteView)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
        notificationManager.notify(DownloadServiceConstants.NOTIFICATION_ID, notificationBuilder.build())
    }
}