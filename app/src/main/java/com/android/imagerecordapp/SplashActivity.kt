package com.android.imagerecordapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.DelicateCoroutinesApi

@SuppressLint("CustomSplashScreen")
@DelicateCoroutinesApi
class SplashActivity : Activity() {

    private val duration: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }, duration)
    }

    override fun onPause() {
        super.onPause()
        println("Splash onPause")
    }

    override fun onStop() {
        super.onStop()
        println("Splash onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Splash onDestroy")
    }
}