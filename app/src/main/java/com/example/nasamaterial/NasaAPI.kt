package com.example.nasamaterial
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaAPI {
    @GET(Constants.NASA_APOD)
    fun getAstronomyPictureOfTheDay(
    @Query("api_key") apiKey: String = "DEMO_KEY"
    ): Call<NasaApodDTO>
}
