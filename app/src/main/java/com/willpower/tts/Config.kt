package com.willpower.tts

import android.os.Environment

object Config {
    const val test_content = "假如我是一只鸟，我也就应用嘶哑的喉咙歌唱。这被暴风雨所打击的土地，这永远汹涌着我们的悲愤的河流，这无止息地吹刮着的激怒的风，和那来自林间的无比温柔的黎明。"
    val sdcard = Environment.getExternalStorageDirectory()?.absolutePath
    val ttsPath = sdcard + "/" + BuildConfig.APPLICATION_ID + "/voice/"
    const val appId = "5c6a7753"
}
