package com.example.legalease.ui.languageSelection

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.preference.PreferenceManager
import java.util.Locale

object LocaleManager {
    private const val PREF_LANGUAGE = "pref_language"

    fun setLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale)
        } else {
            configuration.locale = locale
        }

        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)

        // Save the selected language in shared preferences
        saveLanguage(context, language)
    }

    fun loadSavedLanguage(context: Context): String {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(PREF_LANGUAGE, "en") ?: "en"
    }

    private fun saveLanguage(context: Context, language: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(PREF_LANGUAGE, language)
        editor.apply()
    }
}

