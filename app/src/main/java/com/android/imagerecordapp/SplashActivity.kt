package com.android.imagerecordapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

@SuppressLint("CustomSplashScreen")
@DelicateCoroutinesApi
class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        runBlocking {
            delay(2000)
        }

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
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