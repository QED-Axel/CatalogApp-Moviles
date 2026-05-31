package com.example.catalogapp.domain.repository

import com.example.catalogapp.domain.model.MediaItem
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getFavorites(): Flow<List<MediaItem>>
    fun isFavorite(id: Int): Flow<Boolean>
    suspend fun addFavorite(item: MediaItem)
    suspend fun removeFavorite(id: Int)
}
