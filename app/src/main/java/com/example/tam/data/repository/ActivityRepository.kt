package com.example.tam.data.repository

import com.example.tam.data.api.ApiService
import com.example.tam.data.model.VolunteerActivity

class ActivityRepository(private val apiService: ApiService) {
    suspend fun getActivities(): List<VolunteerActivity> {
        return apiService.getActivities()
    }
}
