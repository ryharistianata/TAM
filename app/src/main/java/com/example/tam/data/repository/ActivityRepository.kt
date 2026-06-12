package com.example.tam.data.repository

import com.example.tam.data.model.response.VolunteerResponse
import com.example.tam.data.remote.api.VolunteerApi

class ActivityRepository(private val apiService: VolunteerApi) {
    suspend fun getActivities(): List<VolunteerResponse> {
        return apiService.getActivities()
    }
}
