package com.willpower.tts.window

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.willpower.tts.R

class ConfigDialog : Dialog {
    private var mContext: Context
    private var tvSpeech: TextView? = null
    private var tvPitch: TextView? = null
    private var tvVolume: TextView? = null
    private var imgCloseConfig: ImageView? = null
    private var seekSpeech: SeekBar? = null
    private var seekPitch: SeekBar? = null
    private var seekVolume: SeekBar? = null
    private var speed: Int = 0
    private var pitch: Int = 0
    private var volume: Int = 0
    private var mSharedPreferences: SharedPreferences? = null

    constructor(context: Context) : this(context, R.style.QMUI_Dialog)

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {
        this.mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (window != null) {
            val wmLp = window.attributes
            wmLp.width = MATCH_PARENT
            wmLp.height = MATCH_PARENT
            window.attributes = wmLp
        }
        mSharedPreferences = context.getSharedPreferences("tts", AppCompatActivity.MODE_PRIVATE)
        speed = mSharedPreferences!!.getInt("speed", 50)
        pitch = mSharedPreferences!!.getInt("pitch", 50)
        volume = mSharedPreferences!!.getInt("volume", 50)
        val root = LayoutInflater.from(context).inflate(R.layout.dialog_config, null)
        val params = ViewGroup.LayoutParams(QMUIDisplayHelper.getScreenWidth(context), QMUIDisplayHelper.getScreenHeight(context))
        imgCloseConfig = root.findViewById(R.id.imgCloseConfig)
        tvSpeech = root.findViewById(R.id.tvSpeech)
        tvPitch = root.findViewById(R.id.tvPitch)
        tvVolume = root.findViewById(R.id.tvVolume)
        seekSpeech = root.findViewById(R.id.seekSpeech)
        seekPitch = root.findViewById(R.id.seekPitch)
        seekVolume = root.findViewById(R.id.seekVolume)
        imgCloseConfig?.setOnClickListener {
            dismiss()
        }
        seekSpeech!!.setOnSeekBarChangeListener(listener)
        seekPitch!!.setOnSeekBarChangeListener(listener)
        seekVolume!!.setOnSeekBarChangeListener(listener)
        seekSpeech!!.progress = speed
        seekPitch!!.progress = pitch
        seekVolume!!.progress = volume
        changeUI()
        setContentView(root, params)
        setCancelable(true)
        setOnDismissListener(dismissCallback)
    }

    private val listener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            when (seekBar?.id) {
                R.id.seekSpeech -> speed = progress
                R.id.seekPitch -> pitch = progress
                R.id.seekVolume -> volume = progress
            }
            changeUI()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    }

    private val dismissCallback = DialogInterface.OnDismissListener {
        mSharedPreferences!!.edit()
                .putInt("speech", speed)
                .putInt("pitch", pitch)
                .putInt("volume", volume)
                .apply()
    }

    private fun changeUI() {
        tvSpeech?.text = "$speed"
        tvPitch?.text = "$pitch"
        tvVolume?.text = "$volume"
    }

}
