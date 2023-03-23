package com.fajar.githubsearchapp.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import com.fajar.githubsearchapp.databinding.ActivitySplashScreenBinding
import com.fajar.githubsearchapp.ui.main.MainActivity
import com.fajar.githubsearchapp.ui.setting.PreferencesKeys
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private val timeSplashScreen: Long = 3000

    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        observeDarkMode()
        Handler().postDelayed(Runnable {
            val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }, timeSplashScreen)
    }

    private val dataStore: DataStore<Preferences> by lazy {
        applicationContext.createDataStore(name = "settings")

    }

    private fun observeDarkMode() {
        lifecycleScope.launch {
            dataStore.data.collect { settings ->
                val isDarkMode = settings[PreferencesKeys.IS_DARK_MODE] ?: false
                AppCompatDelegate.setDefaultNightMode(
                    if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }
    }
}