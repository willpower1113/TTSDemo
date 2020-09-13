package com.willpower.tts

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import java.io.File

class ShareHelper{

    companion object {
        fun toWeChat(context:Context,path:String){
            val intent = Intent(Intent.ACTION_SEND)
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            intent.type = "*/*"
            val contentUri = if (Build.VERSION.SDK_INT >= 24)
                FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", File(path))
            else
                Uri.parse(path)
            intent.putExtra(Intent.EXTRA_STREAM, contentUri)
            context.startActivity(Intent.createChooser(intent, "发送"))
        }
    }

}
