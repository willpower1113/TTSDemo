package com.willpower.tts.window

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.willpower.tts.ProgressView
import com.willpower.tts.R

class AppToast : Dialog {
    companion object {
        /**
         * 不显示任何icon
         */
        const val ICON_TYPE_NOTHING = 0
        /**
         * 显示 Loading 图标
         */
        const val ICON_TYPE_LOADING = 1
        /**
         * 显示成功图标
         */
        const val ICON_TYPE_SUCCESS = 2
        /**
         * 显示失败图标
         */
        const val ICON_TYPE_FAIL = 3
        /**
         * 显示信息图标
         */
        const val ICON_TYPE_INFO = 4
    }

    private var mContext: Context
    private var mHandler = Handler(Looper.getMainLooper())
    private var loading: ProgressView? = null
    private var icon: ImageView? = null
    private var tipWord: TextView? = null
    private var imgGroup: FrameLayout? = null

    constructor(context: Context) : this(context, R.style.QMUI_TipDialog)

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {
        this.mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (window != null) {
            val wmLp = window.attributes
            wmLp.width = ViewGroup.LayoutParams.MATCH_PARENT
            window.attributes = wmLp
        }
        setCancelable(false)
        setContentView(R.layout.dialog_app_toast)
    }

    fun show(iconType: Int, content: String) {
        show(iconType, content, 2000)
    }

    fun show(iconType: Int, content: String, duration: Long) {
        if (!isShowing) {
            show()
        }
        if (imgGroup == null) {
            loading = findViewById(R.id.loading)
            icon = findViewById(R.id.icon)
            tipWord = findViewById(R.id.tipWord)
            imgGroup = findViewById(R.id.imgGroup)
        }
        if (iconType == ICON_TYPE_NOTHING) {
            imgGroup!!.visibility = View.GONE
        } else {
            imgGroup!!.visibility = View.VISIBLE
            if (iconType == ICON_TYPE_LOADING) {
                loading!!.visibility = View.VISIBLE
                icon!!.visibility = View.GONE
            } else {
                loading!!.visibility = View.GONE
                icon!!.visibility = View.VISIBLE
                when (iconType) {
                    ICON_TYPE_FAIL -> {
                        icon!!.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.qmui_icon_notify_error))
                    }
                    ICON_TYPE_SUCCESS -> {
                        icon!!.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.qmui_icon_notify_done))
                    }
                    ICON_TYPE_INFO -> {
                        icon!!.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.qmui_icon_notify_info))
                    }
                }
            }
        }
        tipWord!!.text = content
        if (duration != -1L)
            mHandler.postDelayed({ dismiss() }, duration)
    }
}
