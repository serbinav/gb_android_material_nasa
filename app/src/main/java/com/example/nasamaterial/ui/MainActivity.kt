package com.example.nasamaterial.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nasamaterial.R
import com.example.nasamaterial.Storage

class MainActivity : AppCompatActivity(), FragmentChips.OnThemeChanged {

    private lateinit var prefs: Storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = Storage(applicationContext)
        when (prefs.getTheme()) {
            0 -> setTheme(R.style.Theme_NasaMaterial)
            1 -> setTheme(R.style.Theme_ExoticsAndDurability)
            2 -> setTheme(R.style.Theme_SeaWave)
        }

        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FragmentMain.newInstance())
            .commitNow()
    }

    override fun onThemeChanged(theme: Int) {
        when (theme) {
            0 -> {
                prefs.setTheme(0)
                recreate()
            }
            1 -> {
                prefs.setTheme(1)
                recreate()
            }
            2 -> {
                prefs.setTheme(2)
                recreate()
            }
        }
    }
}

//https://www.materialpalette.com/
//Canva
//- * Используйте свои шрифты в приложении.
