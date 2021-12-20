package com.example.downloadfiles.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.downloadfiles.R
import com.example.downloadfiles.base.Constants
import com.example.downloadfiles.models.FileInfo
import com.example.downloadfiles.repository.FileRepositoryImpl
import com.example.downloadfiles.ui.viewmodel.ARRAY_SIZE
import com.example.downloadfiles.ui.viewmodel.FileViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import kotlin.math.pow
import kotlin.math.roundToInt

class DownloadFileService: Service() {

private lateinit var notificationManager: NotificationManager
private lateinit var notificationBuilder: NotificationCompat.Builder

    private val fileRepo = FileRepositoryImpl()

    override fun onBind(p0: Intent?): IBinder?  = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
         super.onStartCommand(intent, flags, startId)
        val url = intent?.getStringExtra(Constants.FILE_URL_KEY)
        val name = intent?.getStringExtra(Constants.FILE_NAME_KEY)

//        createNotificationChannel()
        notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationBuilder = NotificationCompat.Builder(applicationContext, Constants.NOTIFICATION_CHANNEL)
        Toast.makeText(this,"TEST",Toast.LENGTH_SHORT).show()
        fileRepo.getFiles(url?:"").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = { response->
                saveFile(response.body(), name?:"")
            },
            onError = {},
            onComplete = {})

        stopSelf()
        return START_STICKY
    }

    @SuppressLint("SdCardPath")
    private fun saveFile(response: ResponseBody?, name: String) {
        createNotificationChannel()
        displayNotification(null,progress = 2)
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED != state) {
            return
        }
        var totalFileSize: Int
        var count: Int
        val data = ByteArray(ARRAY_SIZE)
        val fileSize = response?.contentLength()

        val input: InputStream = BufferedInputStream(response?.byteStream(), 1024 * 8)
        val output: OutputStream =
            FileOutputStream("/sdcard/${name}${System.currentTimeMillis()}.mp4")
        var total = 0
        val startTime = System.currentTimeMillis()
        var timeCount = 1
        totalFileSize = (fileSize?.div(1024.0.pow(2.0)))?.toInt() ?: 0

        while (input.read(data).also { count = it } != -1) {
            total += count
            val current = (total / 1024.0.pow(2.0)).roundToInt().toDouble()
            val currentTime = System.currentTimeMillis() - startTime
            Log.d("SERVICE_TEST", totalFileSize.toString())
            val progress = ((total * 100) / (fileSize ?: 0L)).toInt()
            displayNotification(null,progress = progress)
            if (currentTime > 1000 * timeCount) {

                timeCount++
                Log.d(FileViewModel::class.java.simpleName, progress.toString())
            }
            output.write(data, 0, count)
        }
        output.flush()
        output.close()
        input.close()
    }
    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL,
                Constants.NOTIFICATION_CHANNEL,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(false)
            notificationManager.createNotificationChannel(channel)
        }
    }
    @SuppressLint("RemoteViewLayout")
    private fun displayNotification(fileInfo: FileInfo?, progress: Int) {
        val remoteView = RemoteViews(applicationContext.packageName, R.layout.custom_notif)
        remoteView.setImageViewResource(R.id.iv_notif, R.drawable.ic_launcher_background)
        remoteView.setTextViewText(
            R.id.tv_notif_progress,
            "$progress complete)"
        )
        remoteView.setTextViewText(R.id.tv_notif_title, "Downloading ..")
        remoteView.setProgressBar(
            R.id.pb_notif,
            95,
            progress,
            false
        )
        notificationBuilder
            .setContent(remoteView)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
        notificationManager.notify(Constants.NOTIFICATION_ID, notificationBuilder.build())
    }
}