package com.example.nasamaterial

sealed class PictureLoadState {
    data class Success(val nasaData: NasaApodDTO) : PictureLoadState()
    class Error(val error: Throwable) : PictureLoadState()
    object Loading : PictureLoadState()
}
