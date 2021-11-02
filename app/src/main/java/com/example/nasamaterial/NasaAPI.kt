package com.example.nasamaterial

import com.example.nasamaterial.dto.NasaApodDTO
import com.example.nasamaterial.dto.NasaMarsDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

import okhttp3.ResponseBody

interface NasaAPI {
    @GET(Constants.NASA_APOD)
    fun getAstronomyPictureOfTheDay(
        @Query("api_key") apiKey: String = "DEMO_KEY"
    ): Call<NasaApodDTO>

    @GET(Constants.NASA_MARS_PHOTO)
    fun getMarsPicture(
        @Query("sol") sol: Int = 1000,
        @Query("camera") camera: String = "fhaz",
        @Query("api_key") apiKey: String = "DEMO_KEY"
    ): Call<NasaMarsDTO>

    @GET(Constants.NASA_EARTH_PHOTO)
    fun getEarthPicture(
        @Query("lon") lon: Float = 48.583F,
        @Query("lat") lat: Float = 54.418F,
        @Query("dim") dim: Float = 0.20F,
        @Query("api_key") apiKey: String = "DEMO_KEY"
    ): Call<ResponseBody>
}
