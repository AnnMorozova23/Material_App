package com.example.material_app.view

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.material_app.R

class SplashActivity : AppCompatActivity() {
    private val handler: Handler by lazy {
        Handler(mainLooper)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.MyTheme)
        setContentView(R.layout.activity_splash)


        val animator = ObjectAnimator.ofFloat(findViewById<ImageView>(R.id.imageView), View.SCALE_Y,-1f);
        animator.duration = 500
        animator.repeatCount = 5
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()

        handler.postDelayed(Runnable {
            startActivity(Intent(this@SplashActivity,MainActivity::class.java))
        },2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}