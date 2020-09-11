package com.willpower.tts

import java.io.Serializable

data class Record(var voicer: String, var info: String, var absolutePath: String, var createTime: Long, var nan: Boolean) : Serializable