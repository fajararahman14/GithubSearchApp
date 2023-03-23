package com.fajar.githubsearchapp.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import com.fajar.githubsearchapp.databinding.ActivitySettingBinding
import kotlinx.coroutines.launch

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeDarkMode()
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setDarkMode(true)
            } else {
                setDarkMode(false)
            }
        }
    }


    private val dataStore: DataStore<Preferences> by lazy {
        applicationContext.createDataStore(name = "settings")

    }

    private fun setDarkMode(isDarkMode: Boolean) {
        lifecycleScope.launch {
            dataStore.edit { settings ->
                settings[PreferencesKeys.IS_DARK_MODE] = isDarkMode
            }
        }
    }

    private fun observeDarkMode() {
        lifecycleScope.launch {
            dataStore.data.collect { settings ->
                val isDarkMode = settings[PreferencesKeys.IS_DARK_MODE] ?: false
                AppCompatDelegate.setDefaultNightMode(
                    if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
                )
                binding.switchDarkMode.isChecked = isDarkMode
            }
        }
    }

}