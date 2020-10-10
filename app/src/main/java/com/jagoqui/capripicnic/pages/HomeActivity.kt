package com.jagoqui.capripicnic.pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jagoqui.capripicnic.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        val inputDataUser = intent.extras
        val username = inputDataUser?.getString("username").toString()

        welcome.text = "${welcome.text}\n$username"
    }
}