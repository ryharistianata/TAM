package com.example.tam.data.model.response

import com.google.gson.annotations.SerializedName

data class VolunteerResponse(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("location") val location: String,
    @SerializedName("date") val date: String,
    // Mendukung berbagai key image dari JSON agar tidak rusak
    @SerializedName("image") val image: String?,
    @SerializedName("imageUrl") val imageUrl: String?
) {
    val displayImage: String get() = image ?: imageUrl ?: ""
}
