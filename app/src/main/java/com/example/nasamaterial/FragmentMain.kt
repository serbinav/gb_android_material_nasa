package com.example.nasamaterial

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class FragmentMain : Fragment() {

    companion object {
        fun newInstance() = FragmentMain()
    }

    private val viewModel: FragmentMainViewModel by lazy {
        ViewModelProvider(this).get(FragmentMainViewModel::class.java)
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnFloatingChips: FloatingActionButton = view.findViewById(R.id.btn_floating_chips)
        btnFloatingChips.setOnClickListener {
            activity?.let {
                it.supportFragmentManager.beginTransaction()
                    .add(R.id.container, FragmentChips.newInstance())
                    .addToBackStack("")
                    .commit()
            }
        }

        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))

        viewModel.detailsLiveData.observe(viewLifecycleOwner) { renderData(it, view) }
        viewModel.getPharmaFromRemoteSource(Constants.API_KEY)
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun renderData(state: PictureLoadState, view: View) {
        when (state) {
            is PictureLoadState.Success -> {
                val serverResponseData = state.nasaData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    //showError("Сообщение, что ссылка пустая")
                    Toast.makeText(requireContext(), "Link is empty", Toast.LENGTH_LONG).show()
                } else {
                    //showSuccess()
                    val bottomSheetTitle: TextView =
                        view.findViewById(R.id.bottom_sheet_description_header)
                    bottomSheetTitle.text = serverResponseData.title

                    val bottomSheetExplanation: TextView =
                        view.findViewById(R.id.bottom_sheet_description)
                    bottomSheetExplanation.text = serverResponseData.explanation

                    val img: AppCompatImageView = view.findViewById(R.id.img)
                    if (serverResponseData.media_type.equals("video")) {
                        img.setImageResource(R.drawable.ic_camera_recorder_no_video)
                    } else {
                        Picasso
                            .get()
                            .load(url)
                            .into(img)
                    }
                }
            }
            is PictureLoadState.Loading -> {
            }
            is PictureLoadState.Error -> {
                Toast.makeText(requireContext(), state.error.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}