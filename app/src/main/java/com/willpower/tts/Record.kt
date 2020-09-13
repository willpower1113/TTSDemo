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
            var name ="${BuildConfig.APPLICATION_ID}_${file.name}"
            Log.e(TTsHelper.TAG,"写入：$name")
            fos = context.openFileOutput(name, Context.MODE_PRIVATE)
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
        fun read(context: Context, simpleName: String): Record? {
            var record: Record? = null
            val fis: FileInputStream
            val ois: ObjectInputStream
            try {
                var name ="${BuildConfig.APPLICATION_ID}_$simpleName"
                Log.e(TTsHelper.TAG,"读取：$name")
                fis = context.openFileInput(name)
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