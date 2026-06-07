package com.example.tam.api

import com.example.tam.model.VolunteerEvent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("events")
    suspend fun getEvents(): List<VolunteerEvent>

}