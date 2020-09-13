package com.willpower.tts

import android.Manifest
import android.app.Activity
import android.os.Build

import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.util.Log

/**
 * 权限动态申请
 */
class PermissionsHelper {

    companion object {

        const val CODE = 1001

        private var permissions = arrayOf(
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.ACCESS_WIFI_STATE,
                android.Manifest.permission.ACCESS_WIFI_STATE,
                android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        fun check(activity: Activity): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for (permission in permissions) {
                    if (activity.checkSelfPermission(permission) != PERMISSION_GRANTED){
                        Log.e(TTsHelper.TAG,permission)
                        return false
                    }
                }
            }
            return true
        }

        fun request(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!check(activity))
                    activity.requestPermissions(permissions, CODE)
            }
        }
    }
}
