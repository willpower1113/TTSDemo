package com.willpower.tts.window

import android.content.Context
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.willpower.tts.R

class SpeakerWindow(context: Context, listener: OnItemChildClickListener) {
    private var dialog: QMUIDialog

    companion object {
        val speaker = mutableListOf(
                hashMapOf(Pair("speaker", "小燕"), Pair("value", "xiaoyan"), Pair("raw", R.raw.xiaoyan)),
                hashMapOf(Pair("speaker", "许久"), Pair("value", "aisjiuxu"), Pair("raw", R.raw.aisjiuxu)),
                hashMapOf(Pair("speaker", "小萍"), Pair("value", "aisxping"), Pair("raw", R.raw.aisxping)),
                hashMapOf(Pair("speaker", "小婧"), Pair("value", "aisjinger"), Pair("raw", R.raw.aisjinger)),
                hashMapOf(Pair("speaker", "许小宝"), Pair("value", "aisbabyxu"), Pair("raw", R.raw.aisbabyxu))
        )
    }

    init {
        dialog = QMUIDialog.MenuDialogBuilder(context)
                .addItems(arrayOf(speaker[0]["speaker"] as String, speaker[1]["speaker"] as String, speaker[2]["speaker"] as String,
                        speaker[3]["speaker"] as String, speaker[4]["speaker"] as String)
                ) { _, which ->
                    listener.onChoose(which)
                    dismiss()
                }
                .show()
    }

    fun getWindow(): QMUIDialog {
        return this.dialog
    }

    private fun dismiss() {
        dialog.dismiss()
    }

    interface OnItemChildClickListener {
        fun onChoose(position: Int)
    }
}
