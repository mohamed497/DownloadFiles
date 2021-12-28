package com.example.downloadfiles.utils

import android.view.View

fun View.toGone() {
    visibility = View.GONE
}

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.checkClickable() {
    isEnabled = false
}