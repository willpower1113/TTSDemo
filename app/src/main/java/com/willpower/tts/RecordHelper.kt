package com.willpower.tts

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import com.qmuiteam.qmui.util.QMUIDisplayHelper

class RecordHelper(context: Context) {
    private var animation: Animation? = null
    private var mContext = context

    fun show(target: View) {
        animation = TranslateAnimation(-QMUIDisplayHelper.getScreenWidth(mContext).toFloat(), 0F, 0F, 0F)
        animation!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
            }

            override fun onAnimationStart(animation: Animation?) {
                target.visibility = View.VISIBLE
            }
        })
        animation!!.duration = 600
        animation!!.interpolator = LinearInterpolator()
        target.startAnimation(animation)
    }

    fun hide(target: View) {
        animation = TranslateAnimation(0F, -QMUIDisplayHelper.getScreenWidth(mContext).toFloat(), 0F, 0F)
        animation!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                target.visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
        animation!!.duration = 600
        animation!!.interpolator = LinearInterpolator()
        target.startAnimation(animation)
    }
}