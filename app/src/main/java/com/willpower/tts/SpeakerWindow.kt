package com.willpower.tts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.qmuiteam.qmui.util.QMUIDisplayHelper.getScreenHeight
import com.qmuiteam.qmui.util.QMUIDisplayHelper.getScreenWidth
import com.qmuiteam.qmui.widget.popup.QMUIListPopup
import com.qmuiteam.qmui.widget.popup.QMUIPopup.DIRECTION_NONE

class SpeakerWindow(context: Context, listener: OnItemChildClickListener) {
    companion object {
        val speaker = mutableListOf(
                hashMapOf(Pair("speaker", "小燕"), Pair("value", "xiaoyan"), Pair("sex", "nv")),
                hashMapOf(Pair("speaker", "许久"), Pair("value", "aisjiuxu"), Pair("sex", "nan")),
                hashMapOf(Pair("speaker", "小萍"), Pair("value", "aisxping"), Pair("sex", "nv")),
                hashMapOf(Pair("speaker", "小婧"), Pair("value", "aisjinger"), Pair("sex", "nv")),
                hashMapOf(Pair("speaker", "许小宝"), Pair("value", "aisbabyxu"), Pair("sex", "nan"))
        )
    }

    private var listPopup: QMUIListPopup

    init {
        listPopup = QMUIListPopup(context, DIRECTION_NONE, MyAdapter(context, listener))
        listPopup.create((getScreenWidth(context) * 0.8f).toInt(), (getScreenHeight(context) * 0.6f).toInt(), null)
    }

    fun getWindow(): QMUIListPopup {
        return this.listPopup
    }

    fun dismiss() {
        listPopup.dismiss()
    }

    internal inner class MyAdapter(var context: Context, private var listener: OnItemChildClickListener) : BaseAdapter() {

        var holder: ViewHolder? = null

        override fun getCount(): Int {
            return speaker.size
        }

        override fun getItem(position: Int): Map<String, String> {
            return speaker[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_speaker, parent, false)
                holder = ViewHolder(view)
                view.tag = holder
            } else {
                holder = view.tag as ViewHolder
            }
            holder!!.tvSpeaker.text = speaker[position]["speaker"]
            if (speaker[position]["sex"].equals("nan")) {
                holder!!.imgSpeak.setImageResource(R.drawable.icon_nan)
            } else {
                holder!!.imgSpeak.setImageResource(R.drawable.icon_nv)
            }
            holder!!.tvSpeaker.setOnClickListener {
                listener.onChoose(speaker[position]["speaker"]!!, speaker[position]["value"]!!)
                dismiss()
            }
            return view!!
        }

        internal inner class ViewHolder(v: View) {
            var tvSpeaker: TextView = v.findViewById(R.id.tvSpeaker)
            var imgSpeak: ImageView = v.findViewById(R.id.imgSpeak)
        }
    }

    interface OnItemChildClickListener {
        fun onChoose(speaker: String, value: String)
    }
}
