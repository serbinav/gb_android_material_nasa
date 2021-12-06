package com.example.nasamaterial.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nasamaterial.R

class CollapsingToolbarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_NasaMaterial)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collapsing_toolbar)
    }
}