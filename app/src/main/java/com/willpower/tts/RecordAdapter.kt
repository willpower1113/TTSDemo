package com.willpower.tts

import android.util.Log
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class RecordAdapter : BaseQuickAdapter<Record, BaseViewHolder>(R.layout.item_record) {

    private var selectItem: Record? = null

    override fun convert(helper: BaseViewHolder, item: Record) {
        helper.setText(R.id.recordVoicer, item.voicer)
                .setText(R.id.recordInfo, item.info)
                .setText(R.id.recordPath, item.absolutePath.replace(Config.sdcard?:"sdcard","sdcard"))
                .addOnClickListener(R.id.startListener)
                .addOnClickListener(R.id.stopListener)
                .addOnClickListener(R.id.layoutClick)
        if (selectItem != null && selectItem == item) {
            Log.e("ssss","bbbbb")
            helper.setVisible(R.id.stopListener, true)
                    .setGone(R.id.startListener, false)
        } else {
            helper.setVisible(R.id.startListener, true)
                    .setGone(R.id.stopListener, false)
        }
    }

    fun select(position: Int) {
        Log.e("ssss","ccccc")
        select(mData[position])
    }

    fun select(selectItem: Record) {
        this.selectItem = selectItem
        Log.e("ssss","aaaa")
        notifyDataSetChanged()
    }

    fun removeSelect() {
        this.selectItem = null
        notifyDataSetChanged()
    }

}
