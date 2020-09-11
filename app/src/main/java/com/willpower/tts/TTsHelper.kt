package com.willpower.tts

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.iflytek.cloud.*


class TTsHelper(context: Context, listener: OnTtsCallback) {
    companion object {
        const val TAG: String = "TTS-willpower"
    }

    private var ttsInit: Boolean = false
    private var mTts: SpeechSynthesizer? = null
    private var mContext = context.applicationContext
    private var mListener = listener
    private var currentPath: String? = null
    private var currentName: String? = null
    private var record:Record? = null

    init {
        SpeechUtility.createUtility(mContext, SpeechConstant.APPID + "=${Config.appId}")
        mTts = SpeechSynthesizer.createSynthesizer(mContext) {
            if (it != ErrorCode.SUCCESS)
                Log.d(TAG, "初始化失败,错误码：$it")
            ttsInit = (it == ErrorCode.SUCCESS)
        }
    }

    fun startSynthesis(voicer: String, content: String) {
        if (!ttsInit) {
            Log.e(TAG, "please be init first!!")
            return
        }
        setParams(voicer)
        val createTime = System.currentTimeMillis()
        currentName = "$createTime.wav"
        currentPath = Config.ttsPath + currentName
        record = Record(voicer,content,currentPath!!)
        var code = mTts!!.synthesizeToUri(content, currentPath, synthesisListener)
        if (code != ErrorCode.SUCCESS) {
            mListener.onError(code)
        }
    }

    private fun setParams(voicer: String) {
        // 清空参数
        mTts!!.setParameter(SpeechConstant.PARAMS, null)
        // 根据合成引擎设置相应参数
        mTts!!.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD)
        //支持实时音频返回，仅在synthesizeToUri条件下支持
        mTts!!.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1")
        // 设置在线合成发音人
        mTts!!.setParameter(SpeechConstant.VOICE_NAME, voicer)
        //设置合成语速
        mTts!!.setParameter(SpeechConstant.SPEED, "50")
        //设置合成音调
        mTts!!.setParameter(SpeechConstant.PITCH, "50")
        //设置合成音量
        mTts!!.setParameter(SpeechConstant.VOLUME, "50")
        //设置播放器音频流类型
        mTts!!.setParameter(SpeechConstant.STREAM_TYPE, "3")
        // 设置播放合成音频打断音乐播放，默认为true
        mTts!!.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "false")
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mTts!!.setParameter(SpeechConstant.AUDIO_FORMAT, "wav")
        mTts!!.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory().absolutePath + "/msc/tts.pcm")
    }

    private var synthesisListener: SynthesizerListener = object : SynthesizerListener {
        override fun onBufferProgress(percent: Int, beginPos: Int, endPos: Int, info: String?) {
            // 合成进度
            listener.onProgress(percent)
        }

        override fun onSpeakBegin() {
            Log.e(TAG, "onSpeakBegin")
        }

        override fun onSpeakProgress(p0: Int, p1: Int, p2: Int) {
            Log.e(TAG, "onSpeakProgress")
        }

        override fun onEvent(eventType: Int, arg1: Int, arg2: Int, obj: Bundle) {
        }

        override fun onSpeakPaused() {
            Log.e(TAG, "onSpeakPaused")
        }

        override fun onSpeakResumed() {
            Log.e(TAG, "onSpeakResumed")
        }

        override fun onCompleted(error: SpeechError?) {
            Log.e(TAG, "onCompleted：${error.toString()}")
            listener.onSynthesizer()
            record?.save(context)
        }
    }

    interface OnTtsCallback {
        //合成
        fun onSynthesizer()

        //进度
        fun onProgress(percent: Int)

        //异常
        fun onError(code: Int)
    }
}
