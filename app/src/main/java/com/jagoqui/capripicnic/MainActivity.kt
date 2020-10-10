package com.jagoqui.capripicnic

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.jagoqui.capripicnic.auth.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        logo_imageView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_in))

        Handler(Looper.getMainLooper()).postDelayed({
            logo_imageView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_out))
            Handler(Looper.getMainLooper()).postDelayed({
                logo_imageView.visibility = View.GONE
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            },500)
        },2500)
    }
}