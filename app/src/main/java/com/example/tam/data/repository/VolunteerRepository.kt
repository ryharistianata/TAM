package com.example.tam.data.repository

import com.example.tam.data.model.response.VolunteerResponse
import com.example.tam.data.remote.api.VolunteerApi

class VolunteerRepository(private val api: VolunteerApi) {
    suspend fun getActivities(): List<VolunteerResponse> {
        return api.getActivities()
    }
}
