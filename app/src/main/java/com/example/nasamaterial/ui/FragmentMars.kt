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
import com.example.nasamaterial.dto.NasaMarsDTO
import com.example.nasamaterial.viewModel.FragmentMarsViewModel
import com.squareup.picasso.Picasso

class FragmentMars : Fragment() {

    private val viewModel: FragmentMarsViewModel by lazy {
        ViewModelProvider(this).get(FragmentMarsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mars, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.detailsLiveData.observe(viewLifecycleOwner) { renderData(it, view) }
        viewModel.getPharmaFromRemoteSource(Constants.API_KEY)
    }

    private fun renderData(state: PictureLoadState, view: View) {
        when (state) {
            is PictureLoadState.Success<*> -> {
                val serverResponseData = state.nasaData
                if (serverResponseData is NasaMarsDTO) {
                    val url = serverResponseData.photos.first()?.img_src
                    if (url.isNullOrEmpty()) {
                        //showError("Сообщение, что ссылка пустая")
                        Toast.makeText(requireContext(), "Link is empty", Toast.LENGTH_LONG).show()
                    } else {
                        //showSuccess()
                        val img: AppCompatImageView = view.findViewById(R.id.img)
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