package com.example.catalogapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.catalogapp.data.remote.RetrofitClient
import com.example.catalogapp.data.repository.CatalogRepositoryImpl
import com.example.catalogapp.domain.model.MediaItem
import com.example.catalogapp.domain.model.Resource
import com.example.catalogapp.domain.repository.CatalogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatalogViewModel(
    private val repository: CatalogRepository
) : ViewModel() {

    private val _catalogState = MutableStateFlow<Resource<List<MediaItem>>>(Resource.Loading())
    val catalogState: StateFlow<Resource<List<MediaItem>>> = _catalogState.asStateFlow()

    private val _trendingState = MutableStateFlow<Resource<List<MediaItem>>>(Resource.Loading())
    val trendingState: StateFlow<Resource<List<MediaItem>>> = _trendingState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        fetchCatalog()
        fetchTrending()
    }

    fun fetchCatalog() {
        viewModelScope.launch {
            repository.getCatalog().collect { resource ->
                if (resource is Resource.Success && resource.data != null) {
                    _catalogState.value = Resource.Success(resource.data.shuffled())
                } else {
                    _catalogState.value = resource
                }
            }
        }
    }

    fun fetchTrending() {
        viewModelScope.launch {
            repository.getTrending().collect { resource ->
                _trendingState.value = resource
            }
        }
    }

    fun searchMovies(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            fetchCatalog()
            return
        }
        viewModelScope.launch {
            repository.searchMovies(query).collect { resource ->
                _catalogState.value = resource
            }
        }
    }

    fun refreshAll() {
        _searchQuery.value = ""
        fetchCatalog()
        fetchTrending()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = CatalogRepositoryImpl(RetrofitClient.apiService)
                return CatalogViewModel(repository) as T
            }
        }
    }
}
