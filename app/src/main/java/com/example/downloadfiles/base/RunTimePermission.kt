package com.example.downloadfiles.base

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat


class RunTimePermission(private var context: Context) {

    private lateinit var permissionCallback: PermissionCallback

    interface PermissionCallback {

        fun onGranted()
        fun onDenied()
    }

    fun requestPermission(arrPermissionName: List<String>, permissionCallback: PermissionCallback) {
        this.permissionCallback = permissionCallback
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkAllPermisionGranted(arrPermissionName)) {
                (context as Activity).requestPermissions(arrPermissionName.toTypedArray(), Constants.PERMISSION_REQUEST)
            } else {
                permissionCallback.onGranted()
            }
        } else {
            permissionCallback.onGranted()
        }
    }

    private fun checkAllPermisionGranted(arrPermisionName: List<String>): Boolean {
        for (i in arrPermisionName.indices) {
            if (ContextCompat.checkSelfPermission(context, arrPermisionName[i]) !== PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun onRequestPermissionsResult(grantResults: IntArray) {
        for (i in grantResults.indices) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                permissionCallback.onGranted()

            } else {
                permissionCallback.onDenied()
                break
            }
        }
    }


}