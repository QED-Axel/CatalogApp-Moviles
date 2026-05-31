package com.example.catalogapp.data.remote

import com.example.catalogapp.domain.model.MediaItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatalogApiService {
    @GET("catalog")
    suspend fun getCatalog(): Response<List<MediaItem>>

    @GET("catalog/search")
    suspend fun searchMovies(@Query("q") query: String): Response<List<MediaItem>>

    @GET("catalog/trending")
    suspend fun getTrending(): Response<List<MediaItem>>

    @GET("catalog/{id}")
    suspend fun getMediaItemById(@Path("id") id: Int): MediaItem
}
