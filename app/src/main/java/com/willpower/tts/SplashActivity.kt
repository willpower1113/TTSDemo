package com.willpower.tts

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private var mHandler: Handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        QMUIStatusBarHelper.translucent(this, Color.WHITE)
        QMUIStatusBarHelper.setStatusBarLightMode(this)
        if (PermissionsHelper.check(this)) {
            start()
        } else {
            PermissionsHelper.request(this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionsHelper.CODE) {
            start()
        }
    }

    private fun start() {
        mSplashView.post {
            mSplashView.start()
            mHandler.postDelayed({ jump() }, 5000)
        }
    }


    private fun jump() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
