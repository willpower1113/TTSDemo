package com.willpower.tts

import android.content.Context
import android.util.Log
import java.io.*


data class Record(var voicer: String, var info: String, var absolutePath: String) : Serializable {
    fun save(context: Context) {
        val fos: FileOutputStream
        val oos: ObjectOutputStream
        try {
            val file = File(absolutePath)
            fos = context.openFileOutput("${BuildConfig.APPLICATION_ID}_${file.name}", Context.MODE_PRIVATE)
            oos = ObjectOutputStream(fos)
            oos.writeObject(this)
            oos.close()
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TTsHelper.TAG, "save：", e)
        }
    }

    companion object {
        fun read(context: Context, simpleName: Long): Record? {
            var record: Record? = null
            val fis: FileInputStream
            val ois: ObjectInputStream
            try {
                fis = context.openFileInput("${BuildConfig.APPLICATION_ID}_$simpleName")
                ois = ObjectInputStream(fis)
                record = ois.readObject() as Record
                ois.close()
                fis.close()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.e(TTsHelper.TAG, "read：", e)
            }
            return record
        }

        fun list(): List<String> {
            var root = File(Config.ttsPath)
            var array = root.listFiles()
            var data = arrayListOf<String>()
            array.forEach {
                data.add(it.name)
            }
            return data
        }
    }


}