package com.example.tam.data.model

import com.google.gson.annotations.SerializedName

data class VolunteerActivity(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("location") val location: String,
    @SerializedName("date") val date: String,
    @SerializedName("image") val image: String
)
