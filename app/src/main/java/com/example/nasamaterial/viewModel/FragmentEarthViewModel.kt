package com.example.nasamaterial.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasamaterial.Constants
import com.example.nasamaterial.PictureLoadState
import com.example.nasamaterial.RemotePicture
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.ResponseBody

class FragmentEarthViewModel(
    val detailsLiveData: MutableLiveData<PictureLoadState> = MutableLiveData(),
    private val detailsRepository: RemotePicture = RemotePicture(),
) : ViewModel() {

    fun getRemoteSource(apiKey: String) {
        detailsLiveData.value = PictureLoadState.Loading
        detailsRepository.getEarthPhotos(apiKey, callback)
    }

    private val callback = object : Callback<ResponseBody> {
        override fun onResponse(
            call: Call<ResponseBody>,
            response: Response<ResponseBody>
        ) {
            detailsLiveData.postValue(
                if (response.isSuccessful) {
                    response.body()?.let {
                        PictureLoadState.Success(it)
                    }
                } else {
                    PictureLoadState.Error(Throwable(Constants.SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            detailsLiveData.postValue(
                PictureLoadState.Error(
                    Throwable(
                        t.message ?: Constants.REQUEST_ERROR
                    )
                )
            )
        }
    }
}


