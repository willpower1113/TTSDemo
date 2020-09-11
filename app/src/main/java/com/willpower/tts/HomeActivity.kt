package com.willpower.tts

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.qmuiteam.qmui.util.QMUIKeyboardHelper
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.qmuiteam.qmui.widget.popup.QMUIListPopup
import kotlinx.android.synthetic.main.activity_home.*

/**
 * 错误码查询：https://www.xfyun.cn/document/error-code
 */
class HomeActivity : AppCompatActivity(), TTsHelper.OnTtsCallback {
    lateinit var context: Context
    private var speakerWindow: QMUIListPopup? = null
    private var voicer: String? = null//发音人
    private var mSharedPreferences: SharedPreferences? = null
    private lateinit var mTTs: TTsHelper
    private var toast: AppToast? = null
    private var recordHelper: RecordHelper? = null

    companion object {
        const val TAG: String = "TTS-willpower"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        PermissionsHelper.request(this)
        context = this
        speakerSelector()
        synthesis()
        initListener()
        initContent()
        initRecord()
        mTTs = TTsHelper(this, this)
    }

    /**
     * 设置
     */
    private fun initConfig() {
        imgRecord.setOnClickListener { }
    }

    /**
     * 合成记录
     */
    private fun initRecord() {
        recordHelper = RecordHelper(context)
        val mRecordGroup = recordGroup
        imgRecord.setOnClickListener {
            recordHelper!!.show(mRecordGroup)
        }
        imgCloseRecord.setOnClickListener {
            recordHelper!!.hide(mRecordGroup)
        }
        Record.list().forEach()
    }

    /**
     * 发言内容
     */
    private fun initContent() {
        QMUIKeyboardHelper.setVisibilityEventListener(this) { isOpen, _ ->
            ttsEdit.focusChanged(isOpen)
            false
        }
    }

    /**
     * 选择发言人
     */
    private fun speakerSelector() {
        mSharedPreferences = getSharedPreferences("tts", MODE_PRIVATE)
        voicer = mSharedPreferences?.getString("voicer", SpeakerWindow.speaker[0]["speaker"])
        ttsTv.text = voicer
        ttsTv.setOnClickListener {
            if (speakerWindow == null) {
                speakerWindow = SpeakerWindow(context, object : SpeakerWindow.OnItemChildClickListener {
                    override fun onChoose(speaker: String, value: String) {
                        switchSpeaker(speaker, value)
                    }
                }).getWindow()
            }
            speakerWindow?.show(ttsTv)
        }
    }

    /**
     * 试听
     */
    private fun initListener() {
        startListener.setOnClickListener {
            startListener.visibility = View.GONE
            stopListener.visibility = View.VISIBLE
        }

        stopListener.setOnClickListener {
            startListener.visibility = View.VISIBLE
            stopListener.visibility = View.GONE
        }
    }

    /**
     * 合成
     */
    private fun synthesis() {
        ttsButton.setOnClickListener {
            if (ttsEdit.text!!.isEmpty()) {
                showToast(QMUITipDialog.Builder.ICON_TYPE_INFO, "请输入要合成的内容！")
            } else {
                mTTs.startSynthesis(Config.test_content, voicer!!)
            }
        }
    }

    private fun switchSpeaker(speaker: String, value: String) {
        ttsTv.text = speaker
        voicer = value
    }

    override fun onProgress(percent: Int) {
        showProgress(percent)
    }

    override fun onSynthesizer() {
        toast?.dismiss()
    }

    override fun onError(code: Int) {
        showToast(AppToast.ICON_TYPE_FAIL, "异常：$code")
    }

    private fun showToast(iconType: Int, msg: String) {
        if (toast == null)
            toast = AppToast(context)
        toast!!.show(iconType, msg)
    }

    private fun showProgress(percent: Int) {
        if (toast == null)
            toast = AppToast(context)
        toast!!.show(AppToast.ICON_TYPE_LOADING, "合成进度：$percent", -1)
    }
}
