package com.example.catalogapp.data.repository

import com.example.catalogapp.data.remote.CatalogApiService
import com.example.catalogapp.domain.model.MediaItem
import com.example.catalogapp.domain.model.Resource
import com.example.catalogapp.domain.repository.CatalogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class CatalogRepositoryImpl(
    private val apiService: CatalogApiService
) : CatalogRepository {

    override fun getCatalog(): Flow<Resource<List<MediaItem>>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.getCatalog()
            if (response.isSuccessful) {
                emit(Resource.Success(response.body() ?: emptyList()))
            } else {
                emit(Resource.Error("HTTP Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Error: ${e.message}"))
        }
    }

    override fun searchMovies(query: String): Flow<Resource<List<MediaItem>>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.searchMovies(query)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body() ?: emptyList()))
            } else {
                emit(Resource.Error("HTTP Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Error: ${e.message}"))
        }
    }

    override fun getTrending(): Flow<Resource<List<MediaItem>>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.getTrending()
            if (response.isSuccessful) {
                emit(Resource.Success(response.body() ?: emptyList()))
            } else {
                emit(Resource.Error("HTTP Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Error: ${e.message}"))
        }
    }

    override fun getMediaItemById(id: Int): Flow<Resource<MediaItem>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.getMediaItemById(id)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error("HTTP Error: ${e.message()}"))
        } catch (e: IOException) {
            emit(Resource.Error("No se pudo conectar al servidor."))
        } catch (e: Exception) {
            emit(Resource.Error("Ocurrió un error inesperado: ${e.message}"))
        }
    }
}
