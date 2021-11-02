package com.example.nasamaterial.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProvider
import com.example.nasamaterial.Constants
import com.example.nasamaterial.PictureLoadState
import com.example.nasamaterial.R
import com.example.nasamaterial.viewModel.FragmentEarthViewModel
import android.graphics.BitmapFactory
import okhttp3.ResponseBody

class FragmentEarth : Fragment() {

    private val viewModel: FragmentEarthViewModel by lazy {
        ViewModelProvider(this).get(FragmentEarthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_earth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.detailsLiveData.observe(viewLifecycleOwner) { renderData(it, view) }
        viewModel.getRemoteSource(Constants.API_KEY)
    }

    private fun renderData(state: PictureLoadState, view: View) {
        when (state) {
            is PictureLoadState.Success<*> -> {
                val serverResponseData = state.nasaData
                if (serverResponseData is ResponseBody) {
                    val bmp = BitmapFactory.decodeStream(serverResponseData.byteStream())

                    val img: AppCompatImageView = view.findViewById(R.id.img)
                    img.setImageBitmap(bmp)
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