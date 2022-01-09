package com.example.material_app.repository

import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PictureOfTheDayRetrofitImpl {

    companion object {
        private const val BASE_URL = "https://api.nasa.gov/"
    }
//    private val api by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
//            .build()
//            .create(PictureOfTheDayAPI::class.java)
//    }
    fun getRetrofitImpl():PictureOfTheDayAPI{
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
        return retrofit.create(PictureOfTheDayAPI::class.java)
    }


    fun getMarsPictureByDate(earth_date: String, apiKey: String, marsCallbackByDate: Callback<MarsPhotosServerResponseData>) {
        getRetrofitImpl().getMarsImageByDate(earth_date, apiKey).enqueue(marsCallbackByDate)
    }
}