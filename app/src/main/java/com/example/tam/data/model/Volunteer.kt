package com.example.tam.data.model

import com.google.gson.annotations.SerializedName

data class Volunteer(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("imageUrl") val imageUrl: String
)
