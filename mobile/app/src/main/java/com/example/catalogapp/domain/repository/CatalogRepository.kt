package com.example.catalogapp.domain.repository

import com.example.catalogapp.domain.model.MediaItem
import com.example.catalogapp.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {
    fun getCatalog(): Flow<Resource<List<MediaItem>>>
    fun getMediaItemById(id: Int): Flow<Resource<MediaItem>>
}
