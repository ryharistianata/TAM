package com.example.tam.data.remote.api

import com.example.tam.data.endpoint.EndPoint
import com.example.tam.data.model.response.VolunteerResponse
import retrofit2.http.GET

interface VolunteerApi {
    @GET(EndPoint.GET_ACTIVITIES)
    suspend fun getActivities(): List<VolunteerResponse>
}
