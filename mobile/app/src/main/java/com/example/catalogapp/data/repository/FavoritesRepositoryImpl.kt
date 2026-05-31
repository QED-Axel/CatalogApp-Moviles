package com.example.catalogapp.data.repository

import com.example.catalogapp.data.local.FavoriteDao
import com.example.catalogapp.data.local.toDomainModel
import com.example.catalogapp.data.local.toEntity
import com.example.catalogapp.domain.model.MediaItem
import com.example.catalogapp.domain.repository.FavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class FavoritesRepositoryImpl(
    private val favoriteDao: FavoriteDao
) : FavoritesRepository {

    override fun getFavorites(): Flow<List<MediaItem>> {
        return favoriteDao.getAllFavorites().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun isFavorite(id: Int): Flow<Boolean> {
        return favoriteDao.isFavorite(id)
    }

    override suspend fun addFavorite(item: MediaItem) {
        withContext(Dispatchers.IO) {
            favoriteDao.insertFavorite(item.toEntity())
        }
    }

    override suspend fun removeFavorite(id: Int) {
        withContext(Dispatchers.IO) {
            favoriteDao.deleteFavoriteById(id)
        }
    }
}
