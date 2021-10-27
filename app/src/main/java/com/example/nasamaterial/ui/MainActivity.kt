package com.example.nasamaterial.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nasamaterial.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FragmentMain.newInstance())
            .commitNow()
    }
}
