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
import com.example.downloadfiles.models.DownloadStatus
import com.example.downloadfiles.models.DownloadingFileStatus
import com.example.downloadfiles.models.FileInfo
import android.os.SystemClock

import android.app.AlarmManager

import android.app.PendingIntent

class DownloadFileService : Service() {

    private val fileRepo by inject<FileRepositoryImpl>()
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationBuilder: NotificationCompat.Builder

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        val file = intent?.getParcelableExtra<FileInfo>(Constants.FILE_KEY)
        setupNotification()
        observeOnDownloadingFile(file)
        Toast.makeText(this, getString(R.string.downloading), Toast.LENGTH_SHORT).show()

        return START_STICKY
    }

    private fun observeOnDownloadingFile(file: FileInfo?) {

        fileRepo.getFiles(file?.url ?: "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = { response ->
                saveFile(
                    response.body(),
                    file
                )
                stopSelf()
            },
                onError = { throwable ->
                    error(getString(R.string.error), throwable)
                },
                onComplete = {
                    file?.downloadStatus = DownloadStatus.DOWNLOADED
                    displayNotification(
                        DownloadServiceConstants.PROGRESS_MAX,
                        file?.title ?: DownloadServiceConstants.DEFAULT_DOWNLOAD_NAME
                    )
                    debug(getString(R.string.download_completed))
                })

    }

    private fun setupNotification() {
        notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationBuilder =
            NotificationCompat.Builder(
                applicationContext,
                DownloadServiceConstants.NOTIFICATION_CHANNEL
            )
    }

    private fun saveFile(response: ResponseBody?, file: FileInfo?) {
        createNotificationChannel()
        displayNotification(
            progress = DownloadServiceConstants.PROGRESS_INIT,
            name = file?.title ?: DownloadServiceConstants.DEFAULT_DOWNLOAD_NAME
        )
        checkExternalStorageState()
        var count: Int
        val data = ByteArray(DownloadServiceConstants.ARRAY_SIZE)
        val fileSize = response?.contentLength()
        val input = BufferedInputStream(response?.byteStream(), DownloadServiceConstants.INPUT_SIZE)
        val output =
            FileOutputStream("${DownloadServiceConstants.STORAGE}${file?.title}${System.currentTimeMillis()}.mp4")
        var total = 0

        while (input.read(data).also { count = it } != -1) {
            total += count
            val progress = ((total * 100) / (fileSize ?: 1L)).toInt()
            DownloadingFileStatus.emitNewProgressUpdates(progress, file)
            displayNotification(
                progress = progress,
                name = file?.title ?: DownloadServiceConstants.DEFAULT_DOWNLOAD_NAME
            )
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
            "$progress ${getString(R.string.complete)})"
        )
        remoteView.setTextViewText(R.id.tv_notif_title, "${getString(R.string.downloading)} $name")
        remoteView.setProgressBar(
            R.id.pb_notif,
            DownloadServiceConstants.PROGRESS_MAX,
            progress,
            false
        )
        notificationBuilder
            .setContent(remoteView)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
        notificationManager.notify(
            DownloadServiceConstants.NOTIFICATION_ID,
            notificationBuilder.build()
        )
    }


}