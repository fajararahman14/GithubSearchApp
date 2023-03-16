package com.fajar.githubsearchapp.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.fajar.githubsearchapp.R
import com.fajar.githubsearchapp.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    private val timeSplashScreen: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        Handler().postDelayed(Runnable {
            val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }, timeSplashScreen)
    }
}