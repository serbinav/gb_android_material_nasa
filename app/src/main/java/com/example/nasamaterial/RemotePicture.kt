package com.example.nasamaterial

import com.example.nasamaterial.dto.NasaApodDTO
import com.example.nasamaterial.dto.NasaMarsDTO
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemotePicture {
    private val pharmaAPI = Retrofit.Builder()
        .baseUrl(Constants.NASA_API_GOV)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        ).build().create(NasaAPI::class.java)

    fun getPictureOfTheDay(apiKey: String, callback: Callback<NasaApodDTO>) {
        pharmaAPI.getAstronomyPictureOfTheDay(apiKey).enqueue(callback)
    }

    fun getMarsPhotos(apiKey: String, callback: Callback<NasaMarsDTO>) {
        pharmaAPI.getMarsPicture(apiKey = apiKey).enqueue(callback)
    }
}