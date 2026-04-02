package com.example.tam.model

import androidx.annotation.DrawableRes

data class VolunteerEvent(
    val id: Int,
    val namaKegiatan: String,
    val lokasi: String,
    val tanggal: String,
    val deskripsi: String,
    @DrawableRes val imageRes: Int,
    val isFavorite: Boolean = false,
    val isJoined: Boolean = false
)