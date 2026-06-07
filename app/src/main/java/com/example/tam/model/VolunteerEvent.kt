package com.example.tam.model

import com.google.gson.annotations.SerializedName

data class VolunteerEvent(
    @SerializedName("id") val id: Int,
    @SerializedName("nama") val nama: String?,
    @SerializedName("lokasi") val lokasi: String?,
    @SerializedName("waktu") val waktu: String?,
    @SerializedName("deskripsi") val deskripsi: String?,
    @SerializedName("img") val img: String?,
    @SerializedName("harga") val harga: String?,
    val isFavorite: Boolean = false,
    val isJoined: Boolean = false
)