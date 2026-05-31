package com.example.catalogapp.domain.model

data class MediaItem(
    val id: Int,
    val title: String,
    val type: String,
    val synopsis: String,
    val imageUrl: String?,
    val year: Int,
    val genre: String
)
