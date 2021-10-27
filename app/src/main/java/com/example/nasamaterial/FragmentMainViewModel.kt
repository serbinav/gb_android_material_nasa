package com.example.nasamaterial

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentMainViewModel(
    val detailsLiveData: MutableLiveData<PictureLoadState> = MutableLiveData(),
    private val detailsRepository: RemotePicture = RemotePicture(),
) : ViewModel() {

    fun getPharmaFromRemoteSource(apiKey: String) {
        detailsLiveData.value = PictureLoadState.Loading
        detailsRepository.getPictureOfTheDay(apiKey, callback)
    }

    private val callback = object : Callback<NasaApodDTO> {
        override fun onResponse(
            call: Call<NasaApodDTO>,
            response: Response<NasaApodDTO>
        ) {
            val serverResponse: NasaApodDTO? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    PictureLoadState.Error(Throwable(Constants.SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<NasaApodDTO>, t: Throwable) {
            detailsLiveData.postValue(
                PictureLoadState.Error(
                    Throwable(
                        t.message ?: Constants.REQUEST_ERROR
                    )
                )
            )
        }
    }

    fun checkResponse(serverResponse: NasaApodDTO): PictureLoadState {
        serverResponse.apply {
            return if (url == null) {
                PictureLoadState.Error(Throwable(Constants.CORRUPTED_ERROR))
            } else {
                PictureLoadState.Success(serverResponse)
            }
        }
    }
}