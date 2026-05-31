package com.example.catalogapp.data.remote

import com.example.catalogapp.domain.model.MediaItem
import retrofit2.http.GET
import retrofit2.http.Path

interface CatalogApiService {
    @GET("catalog")
    suspend fun getCatalog(): List<MediaItem>

    @GET("catalog/{id}")
    suspend fun getMediaItemById(@Path("id") id: Int): MediaItem
}
