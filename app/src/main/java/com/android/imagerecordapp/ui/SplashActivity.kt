package com.android.imagerecordapp.ui

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.android.imagerecordapp.R
import com.android.imagerecordapp.viewmodel.SplashViewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val mViewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 다크모드 비활성화
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        /**
         * 스플래시 탐지
         */
//        val content: View = findViewById(android.R.id.content)
//        content.viewTreeObserver.addOnPreDrawListener(
//            object : ViewTreeObserver.OnPreDrawListener {
//                override fun onPreDraw(): Boolean {
//                    return if (!mViewModel.isLoading.value) {
//                        content.viewTreeObserver.removeOnPreDrawListener(this)
//                        val intent = Intent(applicationContext, MainActivity::class.java)
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//                        startActivity(intent)
//                        finish()
//                        true
//                    } else {
//                        false
//                    }
//                }
//            }
//        )

        /**
         * 스플래시 끝나고 애니메이션 적용
         */
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_Y,
                0f,
                -splashScreenView.height.toFloat()
            ).run {
                interpolator = AnticipateInterpolator()
                duration = 1000
                doOnEnd { splashScreenView.remove() }
                start()
                addListener(object : Animator.AnimatorListener{
                    override fun onAnimationStart(p0: Animator) {
                    }

                    override fun onAnimationEnd(p0: Animator) {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        startActivity(intent)
                        finish()
                    }

                    override fun onAnimationCancel(p0: Animator) {
                    }

                    override fun onAnimationRepeat(p0: Animator) {
                    }

                })
            }
        }
    }
}