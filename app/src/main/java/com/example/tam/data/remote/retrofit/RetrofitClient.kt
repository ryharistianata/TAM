package com.example.tam.data.remote.retrofit

import com.example.tam.data.endpoint.EndPoint
import com.example.tam.data.remote.api.VolunteerApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val instance: VolunteerApi by lazy {
        Retrofit.Builder()
            .baseUrl(EndPoint.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VolunteerApi::class.java)
    }
}
