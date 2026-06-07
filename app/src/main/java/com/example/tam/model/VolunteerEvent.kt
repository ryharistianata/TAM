package com.example.tam.model

data class VolunteerEvent(
    val id: Int,
    val namaKegiatan: String,
    val lokasi: String,
    val tanggal: String,
    val deskripsi: String,
    val imageUrl: String,
    val harga: String,
    val isFavorite: Boolean = false,
    val isJoined: Boolean = false
)