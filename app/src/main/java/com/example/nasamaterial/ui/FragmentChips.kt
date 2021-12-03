package com.example.nasamaterial.ui

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nasamaterial.viewModel.FragmentChipsViewModel
import com.example.nasamaterial.R
import com.google.android.material.chip.Chip
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.widget.AppCompatTextView

class FragmentChips : Fragment() {

    interface OnThemeChanged {
        fun onThemeChanged(theme: Int)
    }

    private lateinit var viewModel: FragmentChipsViewModel
    private lateinit var onThemeChanged: OnThemeChanged

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnThemeChanged) {
            onThemeChanged = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chips_start, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentChipsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val chipDefault: Chip = view.findViewById(R.id.chip_default_theme)
        chipDefault.setOnClickListener {
            onThemeChanged.onThemeChanged(0)
        }

        val chipExoticsDurability: Chip = view.findViewById(R.id.chip_exotics_durability_theme)
        chipExoticsDurability.setOnClickListener {
            onThemeChanged.onThemeChanged(1)
        }

        val chipSeaWave: Chip = view.findViewById(R.id.chip_sea_wave_theme)
        chipSeaWave.setOnClickListener {
            onThemeChanged.onThemeChanged(2)
        }

        val textBig: AppCompatTextView = view.findViewById(R.id.text_big)
        textBig.setMovementMethod(ScrollingMovementMethod())
    }
}