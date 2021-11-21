package com.example.nasamaterial

import android.content.Context
import android.content.SharedPreferences

class Storage(context: Context) {
    private val pref: SharedPreferences by lazy {
        context.getSharedPreferences("app_theme", Context.MODE_PRIVATE)
    }

    fun getTheme(): Int {
        return pref.getInt(Constants.KEY_THEME, 0)
    }

    fun setTheme(theme: Int) {
        pref.edit().putInt(Constants.KEY_THEME, theme).apply()
    }
}