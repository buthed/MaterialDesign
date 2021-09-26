package com.example.materialdesign.repository

import com.example.materialdesign.repository.dto.MarsPhotosServerResponseData
import com.example.materialdesign.repository.dto.PODServerResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("date") date: String,
    ): Call<PODServerResponseData>

    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    fun getMarsImageByDate(
        @Query("earth_date") earth_date: String,
        @Query("api_key") apiKey: String,
    ): Call<MarsPhotosServerResponseData>

}