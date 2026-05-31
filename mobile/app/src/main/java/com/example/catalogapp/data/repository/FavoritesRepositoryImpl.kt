package com.example.catalogapp.data.repository

import com.example.catalogapp.data.local.FavoriteDao
import com.example.catalogapp.data.local.toDomainModel
import com.example.catalogapp.data.local.toEntity
import com.example.catalogapp.domain.model.MediaItem
import com.example.catalogapp.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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
        favoriteDao.insertFavorite(item.toEntity())
    }

    override suspend fun removeFavorite(id: Int) {
        favoriteDao.deleteFavoriteById(id)
    }
}
