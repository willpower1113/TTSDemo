package com.willpower.tts

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import java.io.File


object Player {
    private var player: MediaPlayer? = null

    fun play(context: Context, raw: Int, listener: MediaPlayer.OnCompletionListener) {
        if (player != null)
            stop()
        player = MediaPlayer.create(context, raw)
        player?.setOnCompletionListener(listener)
        player!!.start()
    }

    fun play(context: Context, path: String, listener: MediaPlayer.OnCompletionListener) {
        if (player != null)
            release()
        val contentUri = if (Build.VERSION.SDK_INT >= 24)
            FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", File(path))
        else
            Uri.parse(path)
        player = MediaPlayer.create(context, contentUri)
        player?.setOnCompletionListener(listener)
        player!!.start()
    }

    fun pause() {
        player?.pause()
    }

    fun resume() {
        player?.start()
    }

    fun stop() {
        player?.stop()
        player = null
    }

    fun release() {
        player?.release()
        player = null
    }
}
