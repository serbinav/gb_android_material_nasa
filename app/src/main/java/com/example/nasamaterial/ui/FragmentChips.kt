package com.example.nasamaterial.ui

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.method.ScrollingMovementMethod
import android.text.style.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nasamaterial.R
import com.example.nasamaterial.viewModel.FragmentChipsViewModel
import com.google.android.material.chip.Chip

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

        val spannable = SpannableStringBuilder(textBig.text)
        spannable.setSpan(BulletSpan(), 30, 47, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(BulletSpan(), 48, 80, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(Color.RED), 4, 14, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        spannable.insert(14, " золотой")
        spannable.setSpan(StyleSpan(Typeface.BOLD), 4, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val span: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
            }
        }
        textBig.movementMethod = (LinkMovementMethod.getInstance())
        spannable.setSpan(span, 447, 453, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(
            BackgroundColorSpan(Color.BLUE),
            145,
            150,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            QuoteSpan(Color.GREEN),
            190,
            200,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            QuoteSpan(Color.GREEN),
            1037,
            1050,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textBig.text = spannable
    }
}