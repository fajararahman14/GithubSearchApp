package com.fajar.githubsearchapp.ui.setting

import androidx.datastore.preferences.core.preferencesKey

object PreferencesKeys {
    val IS_DARK_MODE = preferencesKey<Boolean>("dark_theme_enabled")
}