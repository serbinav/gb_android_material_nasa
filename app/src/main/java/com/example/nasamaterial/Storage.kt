package com.example.nasamaterial

import android.content.Context
import android.content.SharedPreferences

class Storage(context: Context) {

    private val KEY = "KEY_THEME"
    private val pref: SharedPreferences by lazy {
        context.getSharedPreferences("app_theme", Context.MODE_PRIVATE)}
    
    fun getTheme(): Int {
        return pref.getInt(KEY, 0)
    }

    fun setTheme(theme: Int) {
        pref.edit().putInt(KEY, theme).apply()
    }
}