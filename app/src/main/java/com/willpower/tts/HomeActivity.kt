package com.willpower.tts

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.qmuiteam.qmui.util.QMUIKeyboardHelper
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.willpower.tts.window.AppToast
import com.willpower.tts.window.ConfigDialog
import com.willpower.tts.window.SpeakerWindow
import kotlinx.android.synthetic.main.activity_home.*


/**
 * 错误码查询：https://www.xfyun.cn/document/error-code
 */
class HomeActivity : AppCompatActivity(), TTsHelper.OnTtsCallback {
    lateinit var context: Context
    private var speakerWindow: QMUIDialog? = null
    private var voicerPosition: Int = 0//发音人
    private var mSharedPreferences: SharedPreferences? = null
    private lateinit var mTTs: TTsHelper
    private var toast: AppToast? = null
    private var recordHelper: RecordHelper? = null
    private var recordAdapter: RecordAdapter? = null
    private var configDialog: ConfigDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        context = this
        speakerSelector()
        synthesis()
        initConfig()
        initListener()
        initContent()
        initRecord()
        mTTs = TTsHelper(this, this)
    }

    /**
     * 设置
     */
    private fun initConfig() {
        imgSetting.setOnClickListener {
            if (configDialog == null)
                configDialog = ConfigDialog(context)
            configDialog?.show()
        }
    }

    /**
     * 合成记录
     */
    private fun initRecord() {
        recordHelper = RecordHelper(context)
        val mRecordGroup = recordGroup
        imgRecord.setOnClickListener {
            recordHelper!!.show(mRecordGroup)
            QMUIKeyboardHelper.hideKeyboard(ttsEdit)
            var recordList: ArrayList<Record> = arrayListOf()
            Record.list().forEach {
                Record.read(context, it)?.let { it1 -> recordList.add(it1) }
            }
            recordAdapter?.setNewData(recordList)
        }
        imgCloseRecord.setOnClickListener {
            recordHelper!!.hide(mRecordGroup)
        }
        recordAdapter = RecordAdapter()
        mRecordList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mRecordList.adapter = recordAdapter
        mRecordList.addOnItemTouchListener(object : OnItemChildClickListener() {
            override fun onSimpleItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                when (view?.id) {
                    R.id.startListener -> {
                        Player.play(context, recordAdapter!!.data[position].absolutePath, MediaPlayer.OnCompletionListener { recordAdapter!!.removeSelect() })
                        recordAdapter!!.select(position)
                    }
                    R.id.stopListener -> {
                        Player.stop()
                        recordAdapter!!.removeSelect()
                    }
                    R.id.layoutClick -> {
                        QMUIDialog.MenuDialogBuilder(context)
                                .addItem("发送") { dialog, _ ->
                                    ShareHelper.toWeChat(context, recordAdapter!!.data[position].absolutePath)
                                    dialog.dismiss()
                                }
                                .show()
                    }
                }
            }
        })
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
        voicerPosition = mSharedPreferences?.getInt("voicer", 0)!!
        ttsTv.text = SpeakerWindow.speaker[voicerPosition]["speaker"].toString()
        ttsTv.setOnClickListener {
            if (speakerWindow == null) {
                speakerWindow = SpeakerWindow(context, object : SpeakerWindow.OnItemChildClickListener {
                    override fun onChoose(position: Int) {
                        switchSpeaker(position)
                    }
                }).getWindow()
            }
            speakerWindow?.show()
        }
    }

    /**
     * 试听
     */
    private fun initListener() {
        startListener.setOnClickListener {
            startListener.visibility = View.GONE
            stopListener.visibility = View.VISIBLE
            Player.play(context, SpeakerWindow.speaker[voicerPosition]["raw"] as Int, MediaPlayer.OnCompletionListener {
                startListener.visibility = View.VISIBLE
                stopListener.visibility = View.GONE
                Player.stop()
            })
        }

        stopListener.setOnClickListener {
            startListener.visibility = View.VISIBLE
            stopListener.visibility = View.GONE
            Player.stop()
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
                mTTs.startSynthesis(SpeakerWindow.speaker[voicerPosition]["speaker"].toString(),
                        SpeakerWindow.speaker[voicerPosition]["speaker"].toString(), ttsEdit.text.toString())
            }
        }
    }

    private fun switchSpeaker(position: Int) {
        mSharedPreferences!!.edit().putInt("voicer", position).commit()
        voicerPosition = position
        ttsTv.text = SpeakerWindow.speaker[voicerPosition]["speaker"].toString()
    }

    override fun onProgress(percent: Int) {
        showProgress(percent)
    }

    override fun onSynthesizer() {
        toast?.dismiss()
        showToast(AppToast.ICON_TYPE_SUCCESS,"合成完毕")
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

    override fun onBackPressed() {
        if (recordGroup.visibility == View.VISIBLE) {
            Player.stop()
            recordAdapter?.removeSelect()
            recordHelper!!.hide(recordGroup)
        } else {
            moveTaskToBack(false)
        }
    }

    override fun onDestroy() {
        Player.release()
        super.onDestroy()
    }
}
