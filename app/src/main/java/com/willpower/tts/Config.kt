package com.willpower.tts

import android.os.Environment

object Config {
    val sdcard = Environment.getExternalStorageDirectory()?.absolutePath
    val ttsPath = sdcard + "/" + BuildConfig.APPLICATION_ID + "/voice/"
    const val appId = "5c6a7753"
}
