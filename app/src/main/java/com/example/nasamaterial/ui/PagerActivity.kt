package com.example.nasamaterial.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.nasamaterial.Constants
import com.example.nasamaterial.R
import com.google.android.material.tabs.TabLayout

class PagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)
        val pager: ViewPager = findViewById(R.id.view_pager)
        pager.adapter = ViewPagerAdapter(supportFragmentManager)
        val tab: TabLayout = findViewById(R.id.tab_layout)
        tab.setupWithViewPager(pager)
        tab.getTabAt(Constants.EARTH_FRAGMENT)?.setIcon(R.drawable.ic_earth)
        tab.getTabAt(Constants.MARS_FRAGMENT)?.setIcon(R.drawable.ic_mars)
        tab.getTabAt(Constants.WEATHER_FRAGMENT)?.setIcon(R.drawable.ic_weather)
    }
}