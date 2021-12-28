package com.example.downloadfiles.utils

import android.util.Log
import android.view.View


fun Any.debug(message: Any) {
    Log.d(this::class.java.simpleName, message.toString())
}

fun Any.error(message: String, throwable: Throwable?) {
    Log.e(this::class.java.simpleName, message, throwable)
}


