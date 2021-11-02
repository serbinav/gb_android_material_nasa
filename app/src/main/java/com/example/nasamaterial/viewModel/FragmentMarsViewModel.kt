package com.example.nasamaterial.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasamaterial.Constants
import com.example.nasamaterial.PictureLoadState
import com.example.nasamaterial.RemotePicture
import com.example.nasamaterial.dto.NasaMarsDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentMarsViewModel(
    val detailsLiveData: MutableLiveData<PictureLoadState> = MutableLiveData(),
    private val detailsRepository: RemotePicture = RemotePicture(),
) : ViewModel() {

    fun getRemoteSource(apiKey: String) {
        detailsLiveData.value = PictureLoadState.Loading
        detailsRepository.getMarsPhotos(apiKey, callback)
    }

    private val callback = object : Callback<NasaMarsDTO> {
        override fun onResponse(
            call: Call<NasaMarsDTO>,
            response: Response<NasaMarsDTO>
        ) {
            val serverResponse: NasaMarsDTO? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    PictureLoadState.Error(Throwable(Constants.SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<NasaMarsDTO>, t: Throwable) {
            detailsLiveData.postValue(
                PictureLoadState.Error(
                    Throwable(
                        t.message ?: Constants.REQUEST_ERROR
                    )
                )
            )
        }
    }

    fun checkResponse(serverResponse: NasaMarsDTO): PictureLoadState {
        serverResponse.apply {
            val img = photos.first()?.img_src
            return if (img == null) {
                PictureLoadState.Error(Throwable(Constants.CORRUPTED_ERROR))
            } else {
                PictureLoadState.Success(serverResponse)
            }
        }
    }
}