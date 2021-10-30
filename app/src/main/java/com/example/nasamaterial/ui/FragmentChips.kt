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

class FragmentChips : Fragment() {

    interface OnThemeChanged {
        fun onThemeChanged(theme: Int)
    }

    companion object {
        fun newInstance() = FragmentChips()
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
        return inflater.inflate(R.layout.fragment_chips, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentChipsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val chipDefault: Chip = view.findViewById(R.id.chip_default)
        chipDefault.setOnClickListener {
            onThemeChanged.onThemeChanged(0)
        }

        val chipExoticsDurability: Chip = view.findViewById(R.id.chip_exotics_durability)
        chipExoticsDurability.setOnClickListener {
            onThemeChanged.onThemeChanged(1)
        }

        val chipSeaWave: Chip = view.findViewById(R.id.chip_sea_wave)
        chipSeaWave.setOnClickListener {
            onThemeChanged.onThemeChanged(2)
        }
    }
}