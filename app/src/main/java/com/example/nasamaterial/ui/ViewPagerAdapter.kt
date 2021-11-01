package com.example.nasamaterial.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.nasamaterial.Constants

class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = arrayOf(FragmentEarth(), FragmentMars(), FragmentWeather())
    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            1 -> fragments[Constants.MARS_FRAGMENT]
            2 -> fragments[Constants.WEATHER_FRAGMENT]
            else -> fragments[Constants.EARTH_FRAGMENT]
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            1 -> "Марс"
            2 -> "Погода"
            else -> "Земля"
        }
    }
}