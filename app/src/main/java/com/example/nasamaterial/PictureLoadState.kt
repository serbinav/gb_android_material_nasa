package com.example.nasamaterial

sealed class PictureLoadState {
    data class Success<T>(val nasaData: T) : PictureLoadState()
    class Error(val error: Throwable) : PictureLoadState()
    object Loading : PictureLoadState()
}
